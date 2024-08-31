package hackaton.brecho.Models;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "entregas")
public class Entrega {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_entrega")
    private LocalDateTime dataEntrega;

    @Column(name = "status")
    private String status;

    @Column(name = "CEP")
    private String cep;

    private String cidade;

    private String estado;

    @OneToOne(mappedBy = "entrega")
    private Pedido pedido;

    @OneToOne(mappedBy = "cliente")
    private Cliente cliente;
}
