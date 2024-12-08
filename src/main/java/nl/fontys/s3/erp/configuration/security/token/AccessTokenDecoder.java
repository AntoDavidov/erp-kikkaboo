package nl.fontys.s3.erp.configuration.security.token;

public interface AccessTokenDecoder {
    AccessToken decode(String accessTokenEncoded);
}
