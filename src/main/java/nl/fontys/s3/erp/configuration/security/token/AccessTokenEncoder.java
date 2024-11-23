package nl.fontys.s3.erp.configuration.security.token;

public interface AccessTokenEncoder {
    String encode(AccessToken accessToken);
}
