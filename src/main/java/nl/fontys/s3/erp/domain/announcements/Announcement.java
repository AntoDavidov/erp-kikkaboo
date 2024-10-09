package nl.fontys.s3.erp.domain.announcements;

import lombok.*;
import nl.fontys.s3.erp.domain.users.Department;
import nl.fontys.s3.erp.domain.users.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Announcement {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private User createdBy;
    private Department department;
    private LocalDateTime expirationDate;
    private AnnouncementType type;
    private boolean isDepartmentOnly;
}
