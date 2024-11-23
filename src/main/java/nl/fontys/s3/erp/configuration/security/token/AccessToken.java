package nl.fontys.s3.erp.configuration.security.token;

import java.util.Set;

public interface AccessToken {
    String getSubject();

    Long getEmployeeId();

    Set<String> getRoles();

    boolean hasRole(String roleName);
}
