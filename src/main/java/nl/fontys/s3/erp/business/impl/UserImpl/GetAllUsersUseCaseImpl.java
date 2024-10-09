package nl.fontys.s3.erp.business.impl.UserImpl;


import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.DTOs.UserDTOs.GetUsersResponse;
import nl.fontys.s3.erp.business.UserUseCases.GetAllUsersUseCase;
import nl.fontys.s3.erp.business.impl.converters.UserConverter;
import nl.fontys.s3.erp.domain.users.User;
import nl.fontys.s3.erp.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllUsersUseCaseImpl implements GetAllUsersUseCase {
    private final UserRepository userRepository;

    @Override
    public GetUsersResponse getAllUsers() {
        List<User> users = userRepository.findAll()
                .stream()
                .map(UserConverter::convert)
                .toList();

        return GetUsersResponse.builder().users(users).build();
    }

}
