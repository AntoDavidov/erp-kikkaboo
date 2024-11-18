package nl.fontys.s3.erp.business.DTOs.AnnouncementDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nl.fontys.s3.erp.domain.announcements.AnnouncementType;
import nl.fontys.s3.erp.domain.users.Department;
import nl.fontys.s3.erp.domain.users.User;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UpdateAnnouncementRequest {
    private long id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private Date createdAt;

    @NotNull
    private User createdBy;

    @NotNull
    private Department department;

    @NotNull
    private Date expirationDate;

    @NotNull
    private AnnouncementType type;

    @NotNull
    private boolean isDepartmentOnly;
}
