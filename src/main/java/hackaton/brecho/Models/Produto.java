package hackaton.brecho.Models;

import java.util.Set;

import jakarta.persistence.*;


@Entity
@Table(name = "produtos")
public class Produto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String SKU;

    private String nome;

    private String descricao;

    private double preco;

    private int quantidade;

    // uma categoria pode estar ligada a vários produtos && um produto pode estar ligado a várias categorias
    @ManyToMany
    @JoinTable(
        name = "produto_categoria",
        joinColumns = @JoinColumn(name = "produto_id"),
        inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private Set<Categoria> categorias;

}
