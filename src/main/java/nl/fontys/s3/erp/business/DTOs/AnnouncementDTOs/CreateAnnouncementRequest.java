package nl.fontys.s3.erp.business.DTOs.AnnouncementDTOs;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import nl.fontys.s3.erp.domain.announcements.AnnouncementType;
import nl.fontys.s3.erp.domain.users.Department;
import nl.fontys.s3.erp.domain.users.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAnnouncementRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private User createdBy;

    @NotNull
    private Department department;

    @NotNull
    private LocalDateTime expirationDate;

    @NotNull
    private AnnouncementType type;

    @NotNull
    private boolean isDepartmentOnly;


}
