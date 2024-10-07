package hackaton.brecho.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hackaton.brecho.Models.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    @SuppressWarnings("unchecked")
    Pedido save(Pedido pedido);
}
