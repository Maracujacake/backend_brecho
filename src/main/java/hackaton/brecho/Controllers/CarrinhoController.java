package hackaton.brecho.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import hackaton.brecho.Models.Carrinho;
import hackaton.brecho.Models.Cliente;
import hackaton.brecho.Models.ItemCarrinho;
import hackaton.brecho.Models.Pedido;
import hackaton.brecho.Services.CarrinhoService;
import hackaton.brecho.Services.ClienteService;
import hackaton.brecho.Services.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Carrinho", description = "Operações relacionadas ao carrinho do usuário autenticado(criar, adicionar ou remover produto, transformar em pedido)")
@RestController
@RequestMapping("/breshow/carrinho")
public class CarrinhoController {
    
    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private PedidoService pedidoService; // Supondo que você já tenha um serviço de Pedido

    // Cria um carrinho
    @Operation(summary = "Cria o carrinho do usuário, ele será deletado ao transformar em produto")
    @PostMapping("/criar")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Carrinho> criarCarrinho() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Cliente cliente = clienteService.findByEmail(email);
    
        // Criação do Carrinho
        Carrinho carrinho = new Carrinho();
        carrinho.setCliente(cliente);
        carrinho.setItens(new ArrayList<ItemCarrinho>());
    
        carrinhoService.save(carrinho); // Salva o Carrinho
        return ResponseEntity.ok(carrinho);
    }

    // Adiciona um produto ao carrinho
    @Operation(summary = "Adiciona um produto existente ao carrinho")
    @PostMapping("/adicionar/{sku}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Carrinho> adicionarProduto(@PathVariable String sku, @RequestParam int quantidade) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Cliente cliente = clienteService.findByEmail(email);
        Carrinho carrinho = carrinhoService.adicionarProduto(cliente.getId(), sku, quantidade);
        return ResponseEntity.ok(carrinho);
    }

    // Remove um produto do carrinho
    @Operation(summary = "Deleta um produto do carrinho dado SKU e quantidade deste")
    @DeleteMapping("/remover/{sku}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Carrinho> removerProduto(@PathVariable String sku, @RequestParam int quantidade) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Cliente cliente = clienteService.findByEmail(email);
        Carrinho carrinho = carrinhoService.removerProduto(cliente.getId(), sku, quantidade);
        return ResponseEntity.ok(carrinho);
    }

    
    // Converte o carrinho em um pedido
    @Operation(summary = "Transforma o carrinho do usuário em um pedido e o deleta")
    @PostMapping("/finalizar")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Pedido> finalizarPedido() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Cliente cliente = clienteService.findByEmail(email);
        Pedido pedido = carrinhoService.transformarCarrinhoEmPedido(cliente.getId());
        return ResponseEntity.ok(pedido);
    }

    // Lista todos os itens do carrinho
    @Operation(summary = "Lista todos os itens do carrinho")
    @GetMapping("/itens")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ItemCarrinho>> listarItensCarrinho() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Cliente cliente = clienteService.findByEmail(email);
        List<ItemCarrinho> itens = carrinhoService.listarItensCarrinho(cliente.getId());
        return ResponseEntity.ok(itens);
    }

}
