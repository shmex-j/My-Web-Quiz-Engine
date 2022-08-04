package engine.business;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic().and().csrf().disable().authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "/actuator/shutdown").permitAll()
                .mvcMatchers(HttpMethod.POST, "/api/register").permitAll()
                .mvcMatchers(HttpMethod.GET, "/api/quizzes").authenticated()
                .mvcMatchers(HttpMethod.POST, "/api/quizzes").authenticated()
                .mvcMatchers(HttpMethod.GET, "/api/quizzes/**").authenticated()
                .mvcMatchers(HttpMethod.DELETE, "/api/quizzes/**").authenticated()
                .mvcMatchers(HttpMethod.POST, "/api/quizzes/**/solve").authenticated()
                .anyRequest().permitAll();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
