package hackaton.brecho.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import hackaton.brecho.Models.Carrinho;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    List<Carrinho> findByClienteId(Long clienteId); // todos os carrinhos do cliente

    Carrinho findByClienteIdAndCarrinhoId(Long clienteId, Long carrinhoId); // carrinho especifico

    @SuppressWarnings("unchecked")
    Carrinho save(Carrinho carrinho);

}
