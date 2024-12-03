package nl.fontys.s3.erp.configuration.security.token.impl;

import nl.fontys.s3.erp.configuration.security.token.AccessToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import nl.fontys.s3.erp.configuration.security.token.AccessTokenDecoder;
import nl.fontys.s3.erp.configuration.security.token.AccessTokenEncoder;
import nl.fontys.s3.erp.configuration.security.token.exception.InvalidAccessTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class AccessTokenEncoderDecoderImpl implements AccessTokenEncoder, AccessTokenDecoder {
    private final Key key;

    public AccessTokenEncoderDecoderImpl(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String encode(AccessToken accessToken) {
        Map<String, Object> claimsMap = new HashMap<>();
        if(accessToken.getRole() != null) {
            claimsMap.put("role", accessToken.getRole());
        }
        if (accessToken.getEmployeeId() != null) {
            claimsMap.put("employeeId", accessToken.getEmployeeId());
        }
        if(!CollectionUtils.isEmpty(accessToken.getDepartments())) {
            claimsMap.put("departments", accessToken.getDepartments());
        }

        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(accessToken.getSubject())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(30, ChronoUnit.MINUTES)))
                .addClaims(claimsMap)
                .signWith(key)
                .compact();
    }

    @Override
    public AccessToken decode(String accessTokenEncoded) {
        try {
            Jwt<?, Claims> jwt = Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(accessTokenEncoded);
            Claims claims = jwt.getBody();
            System.out.println("Decoded JWT Claims: " + claims);

            String role = claims.get("role", String.class);
            System.out.println("Decoded Role: " + role);

            if (role == null) {
                role = "UNKNOWN";
            }

            Long employeeId = claims.get("employeeId", Long.class);
            System.out.println("Decoded Employee ID: " + employeeId);


            List<String> departments = claims.get("departments", List.class);
            System.out.println("Decoded Departments: " + departments);


            AccessToken token = new AccessTokenImpl(claims.getSubject(), employeeId, role, departments);
            System.out.println("AccessToken Object: " + token);

            return token;
        } catch (JwtException e) {
            throw new InvalidAccessTokenException("Failed to decode access token:" + e.getMessage());
        }
    }
}
