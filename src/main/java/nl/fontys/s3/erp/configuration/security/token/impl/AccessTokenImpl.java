package nl.fontys.s3.erp.configuration.security.token.impl;



import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.fontys.s3.erp.configuration.security.token.AccessToken;
import nl.fontys.s3.erp.domain.users.Department;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@EqualsAndHashCode
@Getter
public class AccessTokenImpl implements AccessToken {
    private final String subject;
    private final Long employeeId;
    private final String role;
    private final Set<String> departments;

    public AccessTokenImpl(String subject, Long employeeId, String role, Collection<String> departments) {
        this.subject = subject;
        this.employeeId = employeeId;
        this.role = role;
        this.departments = departments != null ? Set.copyOf(departments) : Collections.emptySet();
    }
}
