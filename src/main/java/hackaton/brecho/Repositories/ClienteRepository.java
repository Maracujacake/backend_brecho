package hackaton.brecho.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import hackaton.brecho.Models.Cliente;
import java.util.Optional;
import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

    // Registro || Update de um cliente
    @SuppressWarnings("unchecked")
    Cliente save(Cliente cliente);

    // Read
    Optional<Cliente> findById(Long id);
    Optional<Cliente> findByEmail(String email);
    List<Cliente> findAll();

    // Delete
    void deleteById(Long id);
    void deleteByEmail(String email);
    void delete(Cliente cliente);
}
