package hackaton.brecho.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hackaton.brecho.Models.Usuario;
import hackaton.brecho.Repositories.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Método para criar um usuário
    public Usuario save(Usuario usuario) {
        Usuario user = usuarioRepository.findByEmail(usuario.getEmail());
        if (user != null) {
            System.out.println("Usuário já cadastrado");
            return null;
        }

        return usuarioRepository.save(usuario);
    }

    // Método para buscar um usuário pelo email
    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}
