package ru.kpfu.itis.gnt.registration.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import ru.kpfu.itis.gnt.registration.constants.PathConstants;
import ru.kpfu.itis.gnt.registration.entity.UserRole;


import static ru.kpfu.itis.gnt.registration.constants.PathConstants.*;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
@ComponentScan("ru.kpfu.itis.gnt.registration.security")
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsServiceImpl;

    @Autowired
    public void bindUserDetailsServiceAndPasswordEncoder(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder);
    }

    @Bean
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/article/delete/**").hasAuthority(UserRole.ADMIN.name())
                        .requestMatchers("/article/create/**").hasAuthority(UserRole.ADMIN.name())
                        .requestMatchers("/article/update/**").hasAuthority(UserRole.ADMIN.name())
                        .requestMatchers("/article/list").authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin()
                .loginPage(User.LOGIN)
                .failureHandler((request, response, exception) -> response.sendRedirect(User.LOGIN + "?error=" + exception.getMessage()))
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout().logoutUrl(User.LOGOUT).logoutSuccessUrl("/")
                .and()
                .exceptionHandling()
                .accessDeniedPage(PathConstants.Error.ACCESS_DENIED)
                .and()
                .csrf().disable();
        return http.build();
    }
}
