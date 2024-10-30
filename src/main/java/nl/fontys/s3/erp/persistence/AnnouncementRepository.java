package nl.fontys.s3.erp.persistence;

import nl.fontys.s3.erp.persistence.entity.AnnouncementEntity;

import java.util.List;

public interface AnnouncementRepository {
    AnnouncementEntity findById(long id);

    List<AnnouncementEntity> findAll();

    AnnouncementEntity save(AnnouncementEntity announcementEntity);

    void deleteById(long id);

    boolean existsById(long id);

    List<AnnouncementEntity> findByTitle(String title);


}
