package hackaton.brecho.Services;

import hackaton.brecho.Models.Usuario;
import hackaton.brecho.Repositories.UsuarioRepository; // Altere conforme o nome do seu repositório
import hackaton.brecho.config.CustomUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository; // Repositório para acessar os dados do usuário

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email); // Altere conforme seu método de busca
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com email: " + email);
        }
        return new CustomUserDetails(usuario); // Retorna os detalhes do usuário
    }
}
