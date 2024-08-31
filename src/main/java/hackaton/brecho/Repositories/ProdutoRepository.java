package hackaton.brecho.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hackaton.brecho.Models.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
    Optional<Produto> findById(Long id);
}
