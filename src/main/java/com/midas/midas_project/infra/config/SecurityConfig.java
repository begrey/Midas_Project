package com.midas.midas_project.infra.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.midas.midas_project.infra.enums.ErrorType;
import com.midas.midas_project.infra.security.JwtAuthenticationFilter;
import com.midas.midas_project.infra.security.JwtTokenProvider;
import com.midas.midas_project.model.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] PERMIT_URL_ARRAY = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .authorizeRequests()
                .antMatchers(PERMIT_URL_ARRAY).permitAll()
                .antMatchers("/users/login").permitAll()
                .anyRequest().access("@authorizationChecker.checkRole(request, authentication)")
                .and()
                .cors().disable()
                .exceptionHandling()
                    .authenticationEntryPoint(customAuthenticationEntryPoint)
                    .accessDeniedHandler(webAccessDeniedHandler)
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }

    private final AuthenticationEntryPoint customAuthenticationEntryPoint = (request, response, authenticationException) -> {;
        sendErrorResponse(HttpStatus.UNAUTHORIZED, request, response);
    };

    private final AccessDeniedHandler webAccessDeniedHandler = (request, response, accessDeniedException) -> {
        sendErrorResponse(HttpStatus.FORBIDDEN, request, response);
    };

    /*
        인증 및 인가에서 발생하는 Error Handling
     */
    private void sendErrorResponse(HttpStatus status,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws IOException {
        ErrorType errorType = (ErrorType)request.getAttribute("exception");
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        ErrorResponse errorResponse = ErrorResponse.of(status, errorType.getDetail(), errorType);
        writer.write(new ObjectMapper().writeValueAsString(errorResponse));
        writer.flush();
    }

}
