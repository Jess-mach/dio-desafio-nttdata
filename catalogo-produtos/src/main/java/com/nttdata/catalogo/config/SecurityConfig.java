package com.nttdata.catalogo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;

import java.io.IOException;
import java.util.List;

@Configuration
public class SecurityConfig {

    private static final String TOKEN = "secret-token";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(new TokenAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                .headers(headers -> headers.frameOptions().disable());

        return http.build();
    }

    static class TokenAuthFilter extends AbstractAuthenticationProcessingFilter {
        TokenAuthFilter() {
            super(AnyRequestMatcher.INSTANCE);
        }

        @Override
        public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
                throws IOException {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token ausente");
                return null;
            }
            String token = authHeader.substring(7);
            if (!TOKEN.equals(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
                return null;
            }
            return new UsernamePasswordAuthenticationToken("user", null,
                    List.of(new SimpleGrantedAuthority("ROLE_USER")));
        }

        @Override
        protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                FilterChain chain, Authentication authResult)
                throws IOException, ServletException {
            chain.doFilter(request, response);
        }
    }
}
