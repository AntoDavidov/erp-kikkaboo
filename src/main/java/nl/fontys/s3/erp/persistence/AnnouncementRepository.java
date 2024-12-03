package nl.fontys.s3.erp.persistence;

import nl.fontys.s3.erp.persistence.entity.AnnouncementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<AnnouncementEntity, Long> {

}
