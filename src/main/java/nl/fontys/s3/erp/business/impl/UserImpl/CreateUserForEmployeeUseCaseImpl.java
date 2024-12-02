package nl.fontys.s3.erp.business.impl.UserImpl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.DTOs.UserDTOs.CreateUserForEmployeeRequest;
import nl.fontys.s3.erp.business.DTOs.UserDTOs.CreateUserForEmployeeResponse;
import nl.fontys.s3.erp.business.UserUseCases.CreateUserForEmployeeUseCase;
import nl.fontys.s3.erp.business.exceptions.EmployeeDoesNotExist;
import nl.fontys.s3.erp.business.exceptions.UserAccountAlreadyExistsForEmployee;
import nl.fontys.s3.erp.domain.users.Role;
import nl.fontys.s3.erp.persistence.EmployeeRepository;
import nl.fontys.s3.erp.persistence.UserRepository;
import nl.fontys.s3.erp.persistence.UserRoleRepository;
import nl.fontys.s3.erp.persistence.entity.EmployeeEntity;
import nl.fontys.s3.erp.persistence.entity.UserEntity;
import nl.fontys.s3.erp.persistence.entity.UserRoleEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CreateUserForEmployeeUseCaseImpl implements CreateUserForEmployeeUseCase {
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CreateUserForEmployeeResponse createUserForEmployee(CreateUserForEmployeeRequest request) {
        EmployeeEntity employeeEntity = employeeRepository.findByEmployeeCode(request.getEmployeeCode())
                .orElseThrow(EmployeeDoesNotExist::new);

        if(userRepository.findByEmployeeEntity(employeeEntity).isPresent()){
            throw new UserAccountAlreadyExistsForEmployee();
        }

        UserEntity userEntity = saveNewUser(request, employeeEntity);

        return CreateUserForEmployeeResponse.builder()
                .id(userEntity.getId())
                .build();
    }

    private UserEntity saveNewUser(CreateUserForEmployeeRequest request, EmployeeEntity employeeEntity) {
        UserRoleEntity roleEntity = mapRolesToEntities(request.getRole());

         UserEntity userEntity = UserEntity.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .employee(employeeEntity)
                 .role(roleEntity)
                .build();
         return userRepository.save(userEntity);
    }

    private UserRoleEntity mapRolesToEntities(Role role) {
        return userRoleRepository.findRoleByName(role)
                .orElseGet(() -> UserRoleEntity.builder()
                        .role(role)
                        .build());
    }
}
