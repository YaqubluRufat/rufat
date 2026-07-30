package com.example.demo.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final MyUserDetailsService myUserDetailsService;
    private final Blacklist blacklist;

    public JwtFilter(JwtService jwtService, MyUserDetailsService myUserDetailsService, Blacklist blacklist) {
        this.jwtService = jwtService;
        this.myUserDetailsService = myUserDetailsService;
        this.blacklist = blacklist;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if(header==null || !header.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        String token = header.substring(7).trim();
        String username;
       try {
           username=jwtService.extractByUsername(token);
       }catch (Exception ex){
           filterChain.doFilter(request,response);
           return;
       }
       if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
           UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
           if(jwtService.validation(token,userDetails)){

               if(blacklist.isBlackListed(token)){
                   response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                   return;
               }

               if (!userDetails.isEnabled()) {
                   response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                   return;
               }


               if (!userDetails.isAccountNonLocked()) {
                   response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                   return;
               }
               UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                       new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
               SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

           }


       }filterChain.doFilter(request,response);



    }
}
