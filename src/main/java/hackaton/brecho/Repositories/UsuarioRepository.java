
package hackaton.brecho.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import hackaton.brecho.Models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findById(long id);
    Usuario findByEmail(String email);
}
