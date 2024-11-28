package com.example.examen_02.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    // Security Filter Chain - Cadena de filtros de seguridad
    // Been - Singleton - Tener solo una instancia
    @Autowired
    AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        // Configurar los filtros personalizados
        return httpSecurity
            .csrf().disable()
            .httpBasic(Customizer.withDefaults())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(http -> {
                http.requestMatchers(HttpMethod.GET,"/examen2/obtener").hasAuthority("READ");
                http.requestMatchers(HttpMethod.GET,"/examen2/obtener/{id}").hasAuthority("READ");
                http.requestMatchers(HttpMethod.POST,"/examen2/crear").hasAuthority("CREATE");
                http.requestMatchers(HttpMethod.PUT,"/examen2/actualizar").hasAuthority("UPDATE");
                http.requestMatchers(HttpMethod.DELETE,"/examen2/eliminar").hasAuthority("DELETE");
                http.anyRequest().authenticated();
            })
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Athentication Provider - DAO - Proporcionar los usuarios
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());;
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        return daoAuthenticationProvider;
    }

    // Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder(){
        // Si no queremos la contraseña ecriptada
        return NoOpPasswordEncoder.getInstance();
    }

    // UserDetailService -> Base de datos o suarios en memoria
    @Bean
    public UserDetailsService userDetailsService(){
        // Definir usuarios en memoria
        UserDetails admin = User
                .withUsername("admin").password("admin")
                .roles("ADMIN").authorities("READ", "CREATE", "UPDATE", "DELETE").build();

        UserDetails user = User
                .withUsername("user").password("user")
                .roles("USER").authorities("READ").build();

        UserDetails moderator = User
                .withUsername("moderator").password("moderator")
                .roles("MODERATOR").authorities("READ", "UPDATE").build();

        UserDetails editor = User
                .withUsername("editor").password("editor")
                .roles("EDITOR").authorities("UPDATE").build();

        UserDetails developer = User
                .withUsername("developer").password("developer")
                .roles("DEVELOPER").authorities("READ", "CREATE", "UPDATE", "DELETE").build();

        UserDetails analyst = User
                .withUsername("analyst").password("analyst")
                .roles("ANALYST").authorities("READ", "DELETE").build();

        List<UserDetails> userDetailsList = new ArrayList<UserDetails>();
        userDetailsList.add(admin);
        userDetailsList.add(user);
        userDetailsList.add(editor);
        userDetailsList.add(moderator);
        userDetailsList.add(developer);
        userDetailsList.add(analyst);

        return new InMemoryUserDetailsManager(admin, user, analyst, developer, editor, moderator);
    }
}