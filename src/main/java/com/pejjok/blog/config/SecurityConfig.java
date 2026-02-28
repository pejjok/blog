package com.pejjok.blog.config;

import com.pejjok.blog.domain.UserRole;
import com.pejjok.blog.security.BlogUserDetailsService;
import com.pejjok.blog.security.JwtAuthenticationFilter;
import com.pejjok.blog.services.AuthenticationService;
import com.pejjok.blog.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    JwtAuthenticationFilter authenticationFilter(AuthenticationService authenticationService){
        return new JwtAuthenticationFilter(authenticationService);
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService){
        return new BlogUserDetailsService(userService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,JwtAuthenticationFilter authenticationFilter) throws Exception{
        http.
                authorizeHttpRequests(auth->auth
                        .requestMatchers(HttpMethod.POST,"/api/v1/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET,
                                        "/api/v1/posts/**",
                                        "/api/v1/categories/**",
                                        "/api/v1/tags/**"
                        ).permitAll()
                        .requestMatchers("/api/v1/posts/**", "/api/v1/categories/**", "/api/v1/tags/**")
                        .hasRole(UserRole.EDITOR.withoutPrefix())// POST, PUT, DELETE
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config){
        return config.getAuthenticationManager();
    }

   @Bean
   public RoleHierarchy roleHierarchy(){
        String hierarchy = "ROLE_ADMIN > ROLE_EDITOR\nROLE_EDITOR > ROLE_USER";
        return RoleHierarchyImpl.fromHierarchy(hierarchy);
   }
}
