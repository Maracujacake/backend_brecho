package hackaton.brecho.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import hackaton.brecho.Services.CustomUserDetailsService;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration

public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService; // Injetando o serviço de detalhes do usuário

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .cors()
            .and()
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("breshow/cliente/register").permitAll() // Permite o registro de clientes
                .requestMatchers("breshow/cliente/**").authenticated() // Protege o endpoint /me
                .requestMatchers("/breshow/carrinho/**").hasRole("USER")
                .requestMatchers("/breshow/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
            )
            .httpBasic()
            .and()
            .sessionManagement() // Habilita gerenciamento de sessões
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        return http.build();
    }


    @Bean
    public JwtRequestFilter jwtRequestFilter() {
        return new JwtRequestFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Usar NoOpPasswordEncoder para comparar senhas puras
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // Sem criptografia (apenas para desenvolvimento)
    }
}
