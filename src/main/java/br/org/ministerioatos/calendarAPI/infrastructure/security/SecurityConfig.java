package br.org.ministerioatos.calendarAPI.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        return http
                .csrf(csrf -> csrf.disable()) /// Desabilita a proteção CSRF (Cross-Site Request Forgery).
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))/// Seta a política de criação de sessão como STATELESS, ou seja, sem estado.
                .authorizeHttpRequests(auth -> {
                    auth
                        .requestMatchers("/auth/**").permitAll()                    /// Permite acesso livre a todas as rotas que começam com /auth/
                        .anyRequest().authenticated();                                      /// Exige autenticação para qualquer outra requisição.
                })
                .build();
    }

    /// Configura o AuthenticationManager, que é responsável por gerenciar a autenticação dos usuários,
    /// pois o Spring não consegue injetar sem essa configuração
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /// Configura o PasswordEncoder, que é responsável por codificar as senhas dos usuários.
    /// Aqui estamos usando o BCryptPasswordEncoder, que é um algoritmo de hash seguro.
    /// Mas poderia ser outro algoritmo, dependendo da necessidade da aplicação como
    /// NoOpPasswordEncoder, Pbkdf2PasswordEncoder, SCryptPasswordEncoder, etc.
    @Bean
    public PasswordEncoder passEncoder(){
        return new BCryptPasswordEncoder();
    }
}
