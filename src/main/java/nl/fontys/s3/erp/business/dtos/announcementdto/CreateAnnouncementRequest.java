package nl.fontys.s3.erp.business.dtos.announcementdto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import nl.fontys.s3.erp.domain.announcements.AnnouncementType;
import nl.fontys.s3.erp.domain.users.Department;
import nl.fontys.s3.erp.domain.users.User;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAnnouncementRequest {

    @NotBlank
    private String title;

    @NotBlank
    //@Required combines NotBlank NotNull
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
