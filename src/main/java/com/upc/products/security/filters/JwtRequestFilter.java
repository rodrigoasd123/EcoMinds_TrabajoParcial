package com.upc.products.security.filters;
import com.upc.products.security.services.CustomUserDetailsService;
import com.upc.products.security.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
/*
 (1)
    JwtRequestFilter es un filtro de seguridad personalizado que se encarga de procesar
    las solicitudes HTTP entrantes para verificar la validez de un token JWT (JSON Web Token).
    Este filtro se ejecuta una vez por cada solicitud y se utiliza para autenticar al usuario
    y establecer el contexto de seguridad en la aplicación.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public JwtRequestFilter(CustomUserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);
                System.out.println("USERNAME:" + username);
            } catch (Exception e) {
                System.out.println("Error extracting username from token: " + e.getMessage());
                // Si hay error al extraer el username, continúa sin autenticar
                chain.doFilter(request, response);
                return;
            }
        }

        // Este es el punto clave donde se verifica si el token JWT es válido y se establece
        // la autenticación del usuario en el contexto de seguridad de Spring Security.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                if (jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    //siempre por ser stateless, se debe establecer el contexto de seguridad
                }
            } catch (org.springframework.security.core.userdetails.UsernameNotFoundException e) {
                System.out.println("Usuario no encontrado: " + username + ". Token inválido o usuario eliminado.");
                // No establecer autenticación, continuar sin usuario autenticado
                // Esto permite que endpoints públicos sigan funcionando
            } catch (Exception e) {
                System.out.println("Error during authentication: " + e.getMessage());
                // No establecer autenticación en caso de cualquier otro error
            }
        }
        chain.doFilter(request, response);//ya va al controller o al siguiente filtro en la cadena
    }
}
