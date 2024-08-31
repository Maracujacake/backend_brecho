package hackaton.brecho.Models;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "categorias")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToMany(mappedBy = "categorias")
    private Set<Produto> produtos;
}
