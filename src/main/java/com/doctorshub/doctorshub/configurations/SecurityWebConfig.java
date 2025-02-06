package com.doctorshub.doctorshub.configurations;


import com.doctorshub.doctorshub.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityWebConfig {
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
//                        .ignoringRequestMatchers("/login")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/doctor/**").hasAuthority("ROLE_DOCTOR")
                        .requestMatchers("/user/**").hasAuthority("ROLE_USER")
                        .requestMatchers("/", "/registration", "/login","/about", "/static/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
//                .logout(LogoutConfigurer::permitAll
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL, по которому происходит logout (по умолчанию /logout)
                        .logoutSuccessUrl("/") // URL, на который перенаправляется пользователь после выхода
                        .invalidateHttpSession(true) // Инвалидация сессии
                        .deleteCookies("JSESSIONID") // Удаление куки сессии)
                );

//        http.sessionManagement(session -> session
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//        );
//
//        http.addFilterBefore((request, response, chain) -> {
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            if (auth != null) {
//                System.out.println("Authenticated user: " + auth.getName());
//                System.out.println("Authorities: " + auth.getAuthorities());
//            }
//            chain.doFilter(request, response);
//        }, UsernamePasswordAuthenticationFilter.class);
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
