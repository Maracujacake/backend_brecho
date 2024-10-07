package hackaton.brecho.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hackaton.brecho.Models.Categoria;
import hackaton.brecho.Models.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

    // Criação de novo produto
    @SuppressWarnings("unchecked")
    Produto save(Produto prod);

    // Leitura dos produtos
    List<Produto> findAll();

    // Leitura dos produtos por categoria
    List<Produto> findByCategorias(Categoria categoria);

    // Leitura de um produto
    Optional<Produto> findBySKU(String SKU);

    // Atualização de um produto
    @SuppressWarnings("unchecked")
    Produto saveAndFlush(Produto prod);

    // Deleção de um produto
    void deleteBySKU(String SKU);
}
