package ua.org.shpp.todolist.cofiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui*/**","/v3/api-docs/**","/users/sign-up")
                        .permitAll()
                        .requestMatchers("/users").hasAuthority("ADMIN")
                        .requestMatchers(RegexRequestMatcher.regexMatcher("/users/[^/]*")).hasAuthority("ADMIN")
                        .requestMatchers("/users/{username}/**","/users/{username}").access(new WebExpressionAuthorizationManager("#username == authentication.name"))
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .authenticationProvider(authenticationProvider);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
