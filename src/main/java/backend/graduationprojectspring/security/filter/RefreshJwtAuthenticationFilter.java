package backend.graduationprojectspring.security.filter;

import backend.graduationprojectspring.security.TokenProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshJwtAuthenticationFilter extends JwtAuthenticationFilter {
    private final TokenProvider tokenProvider;

    @Override
    Claims authenticationClaimsTemplateMethod(String token) {
        return tokenProvider.validateRefreshToken(token);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/member/refresh");
    }
}
