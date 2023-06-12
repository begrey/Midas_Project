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

    private static final String[] PERMIT_URL_ARRAY = {
            /* swagger v2 */
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            /* swagger v3 */
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };
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
                .antMatchers(PERMIT_URL_ARRAY).permitAll()
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
