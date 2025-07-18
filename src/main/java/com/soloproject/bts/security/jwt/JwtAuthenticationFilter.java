package com.soloproject.bts.security.jwt;

import com.soloproject.bts.repository.UsersRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UsersRepository usersRepository;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService, UsersRepository usersRepository) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.usersRepository = usersRepository;
    }

    @Override
    //Memeriksa "Tiket" (Authorization Header):
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7); //Mengambil "Tiket" dari "Amplop" (Extract JWT)
        try {
            username = jwtService.extractUsername(jwt); //Membaca Informasi dari "Tiket" (Extract Username):
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) { //Memastikan Belum Ada yang "Login" (SecurityContextHolder):
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username); //Memeriksa Keabsahan "Tiket" (Is Token Valid):
                if (jwtService.isTokenValid(jwt, userDetails)) {

                    //Mengizinkan Masuk Jika "Tiket" Valid (Set Authentication):
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (ExpiredJwtException ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{ \"code\": 400,\"status\": \" Bad Request \", \"message\": \"Token expired. Please login again.\", }");
            return;
        }  catch (MalformedJwtException ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{ \"code\": 400,\"status\": \" Bad Request \",\"message\": \"Invalid token.\" }");
            return;
        } catch (SignatureException ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{ \"code\": 400,\"status\": \" Bad Request \", \"message\": \"JWT signature mismatch: Invalid signature.\"  }");
            return;
        } catch (ClassCastException ex){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{ \"code\": 400,\"status\": \" Bad Request \", \"message\": \"Invalid value format in JWT.\" }");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
