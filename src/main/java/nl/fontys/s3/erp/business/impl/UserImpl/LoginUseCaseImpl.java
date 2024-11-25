package nl.fontys.s3.erp.business.impl.UserImpl;

import lombok.RequiredArgsConstructor;
import nl.fontys.s3.erp.business.DTOs.UserDTOs.LoginRequest;
import nl.fontys.s3.erp.business.DTOs.UserDTOs.LoginResponse;
import nl.fontys.s3.erp.business.UserUseCases.LoginUseCase;
import nl.fontys.s3.erp.business.exceptions.EmployeeAlreadyExistsByCode;
import nl.fontys.s3.erp.business.exceptions.EmployeeDoesNotExist;
import nl.fontys.s3.erp.business.exceptions.InvalidCredentials;
import nl.fontys.s3.erp.configuration.security.token.AccessTokenEncoder;
import nl.fontys.s3.erp.configuration.security.token.impl.AccessTokenImpl;
import nl.fontys.s3.erp.persistence.EmployeeRepository;
import nl.fontys.s3.erp.persistence.UserRepository;
import nl.fontys.s3.erp.persistence.entity.DepartmentEntity;
import nl.fontys.s3.erp.persistence.entity.EmployeeEntity;
import nl.fontys.s3.erp.persistence.entity.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        UserEntity userEntity = userRepository.findByEmail(loginRequest.getEmail());
        if (userEntity == null) {
            throw new InvalidCredentials();
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), userEntity.getPassword())) {
            throw new InvalidCredentials();
        }

        EmployeeEntity employeeEntity = userEntity.getEmployee();
        if(employeeEntity == null) {
            throw new EmployeeDoesNotExist();
        }

        String accessToken = generateAccessToken(userEntity, employeeEntity);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    private String generateAccessToken(UserEntity userEntity, EmployeeEntity employeeEntity) {
        Long employeeId = employeeEntity.getId();
        List<String> departments = employeeEntity.getDepartments()
                .stream()
                .map(DepartmentEntity::getName)
                .toList();
        String role = userEntity.getRole().toString();

        return accessTokenEncoder.encode(
                new AccessTokenImpl(userEntity.getEmail(), employeeId, role, departments)
        );
    }
}


