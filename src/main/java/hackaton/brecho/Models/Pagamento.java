package hackaton.brecho.Models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

public class Pagamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;

    @Column(name = "meio_pagamento")
    private String meioPagamento;

    @Column(name = "valor_pagamento")
    private float valorPagamento;

    @OneToOne(mappedBy = "cliente")
    private Cliente cliente;
}
