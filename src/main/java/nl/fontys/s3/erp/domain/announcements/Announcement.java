package nl.fontys.s3.erp.domain.announcements;

import lombok.*;
import nl.fontys.s3.erp.domain.users.Department;
import nl.fontys.s3.erp.domain.users.User;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Announcement {
    private Long id;
    private String title;
    private String content;
    private Date createdAt;
    private User createdBy;
    private Department department;
    private Date expirationDate;
    private AnnouncementType type;
    private boolean isDepartmentOnly;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Announcement that = (Announcement) o;
        return isDepartmentOnly == that.isDepartmentOnly &&
                Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(content, that.content) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(department, that.department) &&
                Objects.equals(expirationDate, that.expirationDate) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, createdAt, createdBy, department, expirationDate, type, isDepartmentOnly);
    }
}
