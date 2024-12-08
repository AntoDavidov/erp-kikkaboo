package nl.fontys.s3.erp.business.impl.userimpl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.userusecases.DeleteUserUseCase;
import nl.fontys.s3.erp.business.exceptions.UserDoesNotExist;
import nl.fontys.s3.erp.persistence.UserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {
    public final UserRepository userRepository;

    @Override
    public void deleteUser(long userId) {
        if(!userRepository.existsById(userId)){
            throw new UserDoesNotExist();
        }
        userRepository.deleteById(userId);
    }

}
