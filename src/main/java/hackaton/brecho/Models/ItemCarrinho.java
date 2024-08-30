package hackaton.brecho.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "itens_carrinho")
public class ItemCarrinho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carrinho_id", nullable = false)
    private Carrinho carrinho;

    @ManyToOne // 
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    private int quantidade;
}
