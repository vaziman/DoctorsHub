package com.doctorshub.doctorshub.configurations;


import com.doctorshub.doctorshub.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // turning on PreAuthorize
@RequiredArgsConstructor
public class SecurityWebConfig  {
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers("/registration", "/swagger-ui/**", "/v3/api-docs/**"))

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/doctor/**").hasAuthority("ROLE_DOCTOR")
                        .requestMatchers("/user/**").hasAuthority("ROLE_USER")
                        .requestMatchers("/", "/registration", "/login", "/about", "/static/**",
                                "/v3/api-docs/**", "/swagger-ui/**", "/v2/api-docs/**",
                                "/swagger-resources/**")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
//                        .loginPage("/login")
                        .usernameParameter("email")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )

//
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL, по которому происходит logout (по умолчанию /logout)
                        .logoutSuccessUrl("/") // URL, на который перенаправляется пользователь после выхода
                        .invalidateHttpSession(true) // Инвалидация сессии
                        .deleteCookies("JSESSIONID") // Удаление куки сессии)
                );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
        return auth.build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }
}
