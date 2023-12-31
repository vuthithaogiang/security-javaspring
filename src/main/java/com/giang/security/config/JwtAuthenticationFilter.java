package com.giang.security.config;

import com.giang.security.token.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
             @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
         final String authHeader  = request.getHeader("Authorization");
         System.out.println(authHeader);
         final String jwt;
         final String userEmail;

         if(authHeader == null || !authHeader.startsWith("Bearer ")){
             filterChain.doFilter(request, response);
             return ;
         }

         jwt = authHeader.substring(7);
         userEmail = jwtService.extractUsername(jwt);
         System.out.println("Email: " + userEmail);

         if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
             UserDetails userDetails =
                     this.userDetailsService.loadUserByUsername(userEmail);
             var isTokenValid = tokenRepository.findByToken(jwt)
                     .map(t -> !t.isExpired() && !t.isRevoked())
                     .orElse(false);

             System.out.println("Token valid? "  + isTokenValid );
             System.out.println("jwt service check token? " + jwtService.isTokenValid(jwt, userDetails));

             if(jwtService.isTokenValid(jwt, userDetails) && isTokenValid){
                 UsernamePasswordAuthenticationToken authToken =
                         new UsernamePasswordAuthenticationToken(
                                 userDetails,
                                 null,
                                 userDetails.getAuthorities()
                         );
                 System.out.println("role: " +  userDetails.getAuthorities());
                 System.out.println("Request" + request);

                 authToken.setDetails(
                         new WebAuthenticationDetailsSource().buildDetails(request)
                 );

                 System.out.println(authToken.isAuthenticated());
                 SecurityContextHolder.getContext().setAuthentication(authToken);

             }
         }
        filterChain.doFilter(request, response);
    }
}
