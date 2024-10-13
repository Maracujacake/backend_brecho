package hackaton.brecho.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import hackaton.brecho.Models.Carrinho;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    
    // CREATE 
    @SuppressWarnings("unchecked")
    Carrinho save(Carrinho carrinho);


    // READ 
    Carrinho findByClienteIdAndId(Long clienteId, Long id); // carrinho especifico

    Carrinho findByClienteId(Long clienteId); // carrinho ativo do cliente

    List<Carrinho> findAllByClienteId(Long clienteId); // todos os carrinhos do cliente

    // DELETE
    void deleteById(Long id);


}
