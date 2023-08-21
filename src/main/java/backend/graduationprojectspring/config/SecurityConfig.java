package backend.graduationprojectspring.config;

import backend.graduationprojectspring.constant.Role;
import backend.graduationprojectspring.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()//token을 사용하므로 basic 인증 비활성화
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);//jwt를 사용하므로 세션 비활성화

        //먼저 지정된 규칙이 우선 적용
        //TODO 경로 설정 제대로 되었는지 나중에 확인
        http.authorizeHttpRequests()
                .requestMatchers("/admin/**").hasRole(Role.ADMIN.toString())
                .requestMatchers("/member/signup").permitAll()
                .requestMatchers("/member/signin").permitAll()
                .requestMatchers("/member/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/evaluation").authenticated()
                .requestMatchers(HttpMethod.GET, "/**").permitAll()
                .requestMatchers("/**").authenticated();

        http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);

        return http.build();
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
