package nl.fontys.s3.erp.business.impl.announcementsimpl;


import lombok.AllArgsConstructor;
import nl.fontys.s3.erp.business.announcementsusecases.DeleteAnnouncementUseCase;
import nl.fontys.s3.erp.business.exceptions.AnnouncementDoesNotExist;
import nl.fontys.s3.erp.configuration.security.token.AccessToken;
import nl.fontys.s3.erp.persistence.AnnouncementRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteAnnouncementUseCaseImpl implements DeleteAnnouncementUseCase {
    private final AnnouncementRepository announcementRepository;
    private final AccessToken accessToken;

    @Override
    public void deleteAnnouncement(long id) {
        if(!announcementRepository.existsById(id)){
            throw new AnnouncementDoesNotExist();
        }
        announcementRepository.deleteById(id);
    }
}
//ask how to get current user