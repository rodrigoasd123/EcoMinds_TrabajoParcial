package com.upc.products.security.config;

import com.upc.products.security.filters.JwtRequestFilter;
import com.upc.products.security.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    //Inyectando JWT Filter por constructor
    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    //se define como un bean para que pueda ser utilizado en otros lugares, como en el controlador de autenticación
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    // Bean para codificar las contraseñas para ser usando en cualquier parte de la app
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //(2) Definir el SecurityFilterChain como un bean, ya no necesitamos heredar, configuramos toda la seg.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // deshabilitar CSRF ya que no es necesario para una API REST
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login", "/api/auth/authenticate", "/api/auth/register").permitAll()
                        .requestMatchers("/api/test/public").permitAll()
                        .requestMatchers("/api/eventos").permitAll() // Permitir GET público a eventos
                        .requestMatchers("/api/evento/{id}").permitAll() // Permitir GET público a evento específico
                        .requestMatchers("/api/admin/test").hasRole("ADMIN")
                        .requestMatchers("/api/recolecciones/**").hasAnyRole("USER", "ORGANIZADOR", "ADMIN")
                        .requestMatchers("/api/materiales/**").hasAnyRole("USER", "ORGANIZADOR", "ADMIN")
                        .requestMatchers("/api/puntos-acopio/**").hasAnyRole("USER", "ORGANIZADOR", "ADMIN")
                        .requestMatchers("/api/recompensas/**").hasAnyRole("USER", "ORGANIZADOR", "ADMIN")
                        .requestMatchers("/api/canjes/**").hasAnyRole("USER", "ORGANIZADOR", "ADMIN")
                        .requestMatchers("/api/usuarios/**").hasAnyRole("USER", "ORGANIZADOR", "ADMIN")
                        .anyRequest().authenticated() // cualquier endpoint puede ser llamado con tan solo autenticarse
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        // Añadir filtros en orden: CORS -> JWT -> Authentication
        http.addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Configuración CORS para permitir peticiones desde frontend
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*"); // Permitir cualquier origen en desarrollo
        config.addAllowedMethod("*"); // Permitir todos los métodos HTTP
        config.addAllowedHeader("*"); // Permitir todos los headers
        config.addExposedHeader("Authorization");
        source.registerCorsConfiguration("/**", config); // Para todos los paths
        return new CorsFilter(source);
    }
}
