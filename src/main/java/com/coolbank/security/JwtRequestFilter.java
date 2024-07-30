package com.coolbank.security;

import com.coolbank.model.AppComponent;
import com.coolbank.model.Users;
import com.coolbank.service.AuthDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final AuthDetailsServiceImpl authDetailsService;
    private final JwtUtil jwtUtil;

    public JwtRequestFilter(AuthDetailsServiceImpl authDetailsService, JwtUtil jwtUtil) {
        this.authDetailsService = authDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        logger.debug("Received Authorization Header: " + requestTokenHeader);

        String principal = null;
        String receivedJwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            receivedJwtToken = requestTokenHeader.substring(7);
            try {
                principal = jwtUtil.getIdentityFromToken(receivedJwtToken);
                logger.debug("JWT Token issued to: " + principal);
            } catch (IllegalArgumentException e) {
                logger.warn("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                logger.warn("JWT Token has expired");
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }

        if (principal != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtUtil.isUserToken(receivedJwtToken)) {
                Users user = authDetailsService.authenticateUserToken(principal);
                if (jwtUtil.validateUserToken(receivedJwtToken))
                    setUserAuthentication(request, user);
            } else if (jwtUtil.isComponentToken(receivedJwtToken)) {
                AppComponent appComponent = authDetailsService.authenticateComponentToken(principal);
                if (jwtUtil.validateComponentToken(receivedJwtToken))
                    setComponentAuthentication(request, appComponent);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void setUserAuthentication(HttpServletRequest request, Users user) {
        UsernamePasswordAuthenticationToken userNamePassAuthToken = new UsernamePasswordAuthenticationToken(
                user.getEmail(), user.getPassword(), new ArrayList<>());

        userNamePassAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(userNamePassAuthToken);
    }

    private void setComponentAuthentication(HttpServletRequest request, AppComponent appComponent) {
        UsernamePasswordAuthenticationToken userNamePassAuthToken = new UsernamePasswordAuthenticationToken(
                appComponent.getComponentId(), appComponent.getComponentSecret(), new ArrayList<>());

        userNamePassAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(userNamePassAuthToken);
    }

}

