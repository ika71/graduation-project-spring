package backend.graduationprojectspring.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class TokenProvider {
    private final Key accessKey;
    private final Key refreshKey;

    private TokenProvider(@Value("${accessSecretkey}") final String ACCESS_SECRET_KEY,
                          @Value("${refreshSecretKey}") final String REFRESH_SECRET_KEY) {
        byte[] accessKeyBytes = ACCESS_SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        byte[] refreshKeyBytes = REFRESH_SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        this.accessKey = Keys.hmacShaKeyFor(accessKeyBytes);
        this.refreshKey = Keys.hmacShaKeyFor(refreshKeyBytes);
    }

    public String createAccessToken(String id, String role){
        //30분
        Date expiryDate = Date.from(Instant.now().plus(30, ChronoUnit.MINUTES));

        return createToken(id, role, accessKey, expiryDate);
    }
    public Claims validateAccessToken(String token){
        return validateToken(token, accessKey);
    }

    public String createRefreshToken(String id, String role){
        //14일
        Date expiryDate = Date.from(Instant.now().plus(14, ChronoUnit.DAYS));

        return createToken(id, role, refreshKey, expiryDate);
    }
    public Claims validateRefreshToken(String token){
        return validateToken(token, refreshKey);
    }

    private String createToken(String id, String role, Key key, Date expiryDate){
        Claims claims = Jwts.claims();
        claims.setSubject(id);
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(key)
                .setIssuer("GraduationProjectSpring")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();
    }

    /**
     * 토큰의 유효성을 확인 유효하지 않을 시 에러 발생<br>
     * TODO 토큰이 유효하지 않을 시 프론트에 응답을 보내 재로그인을 유도해야 함
     * @param token
     * @return Claims
     */
    private Claims validateToken(String token, Key key){
        return (Claims) Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parse(token)
                .getBody();
    }
}
