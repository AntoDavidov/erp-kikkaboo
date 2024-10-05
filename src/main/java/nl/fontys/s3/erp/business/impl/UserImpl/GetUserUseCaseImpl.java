package nl.fontys.s3.erp.business.impl.UserImpl;


import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.UserUseCases.GetUserUseCase;
import nl.fontys.s3.erp.business.exceptions.UserDoesNotExist;
import nl.fontys.s3.erp.business.impl.converters.UserConverter;
import nl.fontys.s3.erp.domain.users.User;
import nl.fontys.s3.erp.persistence.UserRepository;
import nl.fontys.s3.erp.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetUserUseCaseImpl implements GetUserUseCase {
    private final UserRepository userRepository;

    @Override
    public User getUser(long id) {
        UserEntity user = userRepository.findById(id);
        if(user == null){
            throw new UserDoesNotExist();
        }
        return UserConverter.convert(user);
    }
}
