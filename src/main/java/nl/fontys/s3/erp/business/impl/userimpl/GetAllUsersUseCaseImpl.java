package nl.fontys.s3.erp.business.impl.userimpl;


import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.dtos.userdto.GetUsersResponse;
import nl.fontys.s3.erp.business.userusecases.GetAllUsersUseCase;
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
