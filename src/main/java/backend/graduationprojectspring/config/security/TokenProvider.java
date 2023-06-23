package backend.graduationprojectspring.config.security;

import backend.graduationprojectspring.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenProvider {
    private final Key key;

    private TokenProvider(@Value("${secretkey}") final String SECRET_KEY) {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * member 정보를 토대로 토큰 생성
     * @param member
     * @return
     */
    public String createToken(Member member){
        //기한 하루
        Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));

        Claims claims = Jwts.claims();
        claims.setSubject(String.valueOf(member.getId()));
        claims.put("role", member.getRole());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(key)
                .setIssuer("GraduationProjectSpring")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();
    }

    /**
     * 토큰의 유효성을 확인 유효하지 않을 시 에러 발생
     * TODO 토큰이 유효하지 않을 시 프론트에 응답을 보내 재로그인을 유도해야 함
     * @param token
     * @return Claims
     */
    public Claims validateToken(String token){
        return (Claims) Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parse(token)
                .getBody();
    }
}
