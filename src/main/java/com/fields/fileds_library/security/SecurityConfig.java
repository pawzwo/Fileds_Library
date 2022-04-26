package com.fields.fileds_library.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.headers().frameOptions().sameOrigin();

        httpSecurity.authorizeHttpRequests()
                .antMatchers("/h2/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/users/**").hasAnyAuthority("ADMIN")
                .antMatchers("/users").hasAnyAuthority("ADMIN")
                .antMatchers("/companies").hasAnyAuthority("ADMIN")
                .antMatchers("/companies/**").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/concessions").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/concessions/**").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/fields").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/fields/**").hasAnyAuthority("ADMIN", "USER")
                .anyRequest().authenticated();
        httpSecurity.addFilterBefore(new CustomAuthorizationFilter(jwtUtil, objectMapper), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
