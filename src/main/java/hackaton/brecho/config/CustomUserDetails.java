package hackaton.brecho.config;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import hackaton.brecho.Models.Usuario;

public class CustomUserDetails implements UserDetails {
    private String email;
    private String password;
    private Set<String> roles;

    public CustomUserDetails(Usuario usuario) {
        this.email = usuario.getEmail();
        this.password = usuario.getPassword();
        this.roles = usuario.getRoles();
    }

    @Override
    public String getUsername() {
        return email; // Aqui retornamos o email como nome de usuário
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role)) // Converte cada role em um GrantedAuthority
                .collect(Collectors.toSet());
    }
    
}
