package com.midas.midas_project.infra.config;


import com.midas.midas_project.infra.security.JwtAuthenticationFilter;
import com.midas.midas_project.infra.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable();
        http

                .authorizeRequests()
                .antMatchers( "/users/logout").access("hasRole('ROLE_MASTER')")
//                .antMatchers(HttpMethod.POST,"/user/**").access("hasRole('ROLE_MASTER')")
                .antMatchers("/users/login").permitAll()
                .anyRequest().access("@authorizationChecker.check(request, authentication)")
                .and()
                .cors()
                .and()
//                .exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint)
//                .and()
//                .exceptionHandling().accessDeniedHandler(webAccessDeniedHandler)
//                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }
}
