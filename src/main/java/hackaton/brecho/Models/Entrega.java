package hackaton.brecho.Models;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Table(name = "entregas")
@Data
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

    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
