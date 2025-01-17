package nl.fontys.s3.erp.business.impl.userimpl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.impl.converters.UserConverter;
import nl.fontys.s3.erp.business.userusecases.GetUserByEmployeeIdUseCase;
import nl.fontys.s3.erp.domain.users.User;
import nl.fontys.s3.erp.persistence.UserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetUserByEmployeeIdUseCaseImpl implements GetUserByEmployeeIdUseCase {
    private final UserRepository userRepository;

    @Override
    public User getUserByEmployeeId(long employeeId) {
        return userRepository.findByEmployeeId(employeeId)
                .map(UserConverter::convert)
                .orElseThrow(() -> new IllegalArgumentException("User not found for employeeId: " + employeeId));
    }
}