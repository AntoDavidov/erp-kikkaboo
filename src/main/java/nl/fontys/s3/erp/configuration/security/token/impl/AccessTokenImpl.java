package nl.fontys.s3.erp.configuration.security.token.impl;



import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.fontys.s3.erp.configuration.security.token.AccessToken;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@EqualsAndHashCode
@Getter
public class AccessTokenImpl implements AccessToken {
    private final String subject;
    private final Long employeeId;
    private final Set<String> roles;

    public AccessTokenImpl(String subject, Long employeeId, Collection<String> roles) {
        this.subject = subject;
        this.employeeId = employeeId;
        this.roles = roles != null ? Set.copyOf(roles) : Collections.emptySet();
    }

    @Override
    public boolean hasRole(String roleName) {
        return this.roles.contains(roleName);
    }
}
