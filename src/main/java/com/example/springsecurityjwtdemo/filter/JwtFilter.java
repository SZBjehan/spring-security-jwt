package com.example.springsecurityjwtdemo.filter;

import com.example.springsecurityjwtdemo.service.CustomUserDetailsService;
import com.example.springsecurityjwtdemo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String token = null;
        String username = null;
        //Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmaXJzdFVzZXIiLCJleHAiOjE2NjkyMzY5OTYsImlhdCI6MTY2OTIwMDk5Nn0.z-3srDLqQ9i41a879RHVIAlf6BsCI-8nLBZGo7LmarA
//        Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmaXJzdFVzZXIiLCJleHAiOjE2NjkyNDAyMjQsImlhdCI6MTY2OTIwNDIyNH0.op_v9DN4kKhXnB-0GxTgeovNL6Z02mzE5_K5eruoUl4

        if (authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
            token = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(token);
        }
        if (username!=null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(token, userDetails)){

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.
                        setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
