package backend.graduationprojectspring.security.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

abstract class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = parseBearerToken(request);

        if(token != null && !token.equalsIgnoreCase("null")){
            Claims claims = authenticationClaimsTemplateMethod(token);//토큰이 유효하지 않을 경우 에러 발생

            String memberId = claims.getSubject();
            String role = (String) claims.get("role");

            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
            List<GrantedAuthority> authorities = List.of(authority);

            AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    memberId,
                    null,
                    authorities
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request){
        //http 요청에서 Bearer 토큰을 찾는다
        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

    abstract Claims authenticationClaimsTemplateMethod(String token);
}
