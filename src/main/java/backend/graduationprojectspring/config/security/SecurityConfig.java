package backend.graduationprojectspring.config.security;

import backend.graduationprojectspring.constant.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors()//WebConfig에서 이미 설정했으므로 기본 cors로 설정
                .and()
                .csrf().disable()//쿠키가 아닌 로컬 스토리지를 사용하므로 csrf 비활성화
                .httpBasic().disable()//token을 사용하므로 basic 인증 비활성화
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);//jwt를 사용하므로 세션 비활성화


        http.authorizeHttpRequests()
                .requestMatchers("/member/**").permitAll()// /member/** 경로는 인증 x
                .requestMatchers("/admin/**").hasAuthority(Role.ADMIN.toString())
                .anyRequest().authenticated();// 나머지 경로는 인증 요구

        http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);

        return http.build();
    }
}
