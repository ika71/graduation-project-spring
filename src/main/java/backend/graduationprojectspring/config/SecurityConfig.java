package backend.graduationprojectspring.config;

import backend.graduationprojectspring.constant.Role;
import backend.graduationprojectspring.security.filter.AccessJwtAuthenticationFilter;
import backend.graduationprojectspring.security.filter.JwtExceptionHandlerFilter;
import backend.graduationprojectspring.security.filter.RefreshJwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
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
    private final AccessJwtAuthenticationFilter accessJwtAuthenticationFilter;
    private final RefreshJwtAuthenticationFilter refreshJwtAuthenticationFilter;
    private final JwtExceptionHandlerFilter jwtExceptionHandlerFilter;
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults());
        http.csrf(csrf->csrf.disable());
        http.formLogin(login->login.disable());
        http.httpBasic(basic->basic.disable());
        http.sessionManagement(session->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(request-> request
                .requestMatchers("/admin/**").hasRole(Role.ADMIN.toString())
                .requestMatchers(HttpMethod.POST, "/member/signup").permitAll()
                .requestMatchers(HttpMethod.POST, "/member/signin").permitAll()
                .requestMatchers(HttpMethod.GET, "/image/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/device/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/board/**").permitAll()
                .requestMatchers("/**").authenticated());

        http.addFilterAfter(jwtExceptionHandlerFilter, CorsFilter.class);
        http.addFilterAfter(accessJwtAuthenticationFilter, JwtExceptionHandlerFilter.class);
        http.addFilterAfter(refreshJwtAuthenticationFilter, AccessJwtAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
