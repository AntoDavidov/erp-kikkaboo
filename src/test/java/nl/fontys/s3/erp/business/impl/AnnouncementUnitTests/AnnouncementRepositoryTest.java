package nl.fontys.s3.erp.business.impl.AnnouncementUnitTests;

import jakarta.persistence.EntityManager;
import nl.fontys.s3.erp.domain.announcements.Announcement;
import nl.fontys.s3.erp.domain.announcements.AnnouncementType;
import nl.fontys.s3.erp.domain.users.Department;
import nl.fontys.s3.erp.domain.users.Role;
import nl.fontys.s3.erp.domain.users.Status;
import nl.fontys.s3.erp.persistence.AnnouncementRepository;
import nl.fontys.s3.erp.persistence.entity.AnnouncementEntity;
import nl.fontys.s3.erp.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class AnnouncementRepositoryTest {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private EntityManager em;

    @Test
    void save_shouldSaveAnnouncement() {
        UserEntity user = createUserEntity();
        em.persist(user);
        em.flush();

        AnnouncementEntity announcementEntity = createAnnouncement("New Prices Update",
                "Announcement regarding the latest changes in product prices effective immediately.",
                new Date(),
                user,
                Department.PRODUCT,
                new GregorianCalendar(2024, Calendar.DECEMBER, 31).getTime(),
                AnnouncementType.GENERAL,
                false);

        em.persist(announcementEntity);
        em.flush();

        AnnouncementEntity savedAnnouncement = em.find(AnnouncementEntity.class, announcementEntity.getId());

        assertNotNull(savedAnnouncement.getId());
        assertEquals("New Prices Update", savedAnnouncement.getTitle());
        assertEquals("Announcement regarding the latest changes in product prices effective immediately.", savedAnnouncement.getContent());
        assertEquals(announcementEntity.getCreatedAt(), savedAnnouncement.getCreatedAt());
        assertEquals(user, savedAnnouncement.getCreatedBy());
        assertEquals(Department.PRODUCT, savedAnnouncement.getDepartment());
        assertEquals(new GregorianCalendar(2024, Calendar.DECEMBER, 31).getTime(), savedAnnouncement.getExpirationDate());
        assertEquals(AnnouncementType.GENERAL, savedAnnouncement.getType());
        assertEquals(false, savedAnnouncement.isDepartmentOnly());

    }

    private AnnouncementEntity createAnnouncement(String title, String content, Date createdAt, UserEntity createdBy, Department department, Date expirationDate, AnnouncementType announcementType, boolean isDepartmentOnly) {
        return AnnouncementEntity.builder()
                .title(title)
                .content(content)
                .createdAt(createdAt)
                .createdBy(createdBy)
                .department(department)
                .type(announcementType)
                .expirationDate(expirationDate)
                .isDepartmentOnly(isDepartmentOnly)
                .build();
    };
    private UserEntity createUserEntity() {
        return UserEntity.builder()
                .firstName("John")
                .lastName("Doe")
                .address("123 Main St")
                .email("john.doe@example.com")
                .password("securePassword")
                .phone("1234567890")
                .dateOfBirth(new GregorianCalendar(1990, Calendar.JANUARY, 1).getTime())
                .status(Status.ACTIVE)
                .department(Department.PRODUCT)
                .role(Role.MANAGER)
                .salary(BigDecimal.valueOf(50000))
                .build();
    }

}

