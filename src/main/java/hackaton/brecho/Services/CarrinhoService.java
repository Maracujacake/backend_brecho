package hackaton.brecho.Services;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hackaton.brecho.Models.Carrinho;
import hackaton.brecho.Models.ItemCarrinho;
import hackaton.brecho.Models.ItemPedido;
import hackaton.brecho.Models.Pedido;
import hackaton.brecho.Models.Produto;
import hackaton.brecho.Repositories.CarrinhoRepository;
import hackaton.brecho.Repositories.PedidoRepository;
import hackaton.brecho.Repositories.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CarrinhoService {
    private final CarrinhoRepository carrinhoRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoRepository pedidoRepository;

    @Autowired
    public CarrinhoService(CarrinhoRepository carrinhoRepository, ProdutoRepository produtoRepository, PedidoRepository pedidoRepository) {
        this.carrinhoRepository = carrinhoRepository;
        this.produtoRepository = produtoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    //CREATE
    public Carrinho save(Carrinho carrinho) {
        return carrinhoRepository.save(carrinho);
    }

    //READ
    public Carrinho findById(Long id) {
        return carrinhoRepository.findById(id).orElse(null);
    }

    public Carrinho findByClienteIdAndId(Long clienteId, Long carrinhoId) {
        return carrinhoRepository.findByClienteIdAndId(clienteId, carrinhoId);
    }

    // Retorna o carrinho ativo do cliente
    public Carrinho findByClienteId(Long clienteId) {
        return carrinhoRepository.findByClienteId(clienteId);
    }

    // adição de produtos ao carrinho
 public Carrinho adicionarProduto(Long clienteId, String SKU, int quantidade) {
    // Busca o carrinho pelo cliente e ID
    Carrinho carrinho = carrinhoRepository.findByClienteId(clienteId);
    if (carrinho == null) {
        throw new EntityNotFoundException("Carrinho não encontrado.");
    }

    // Busca o produto pelo SKU
    Optional<Produto> produtoOptional = produtoRepository.findBySKU(SKU);
    if (produtoOptional.isEmpty()) {
        throw new EntityNotFoundException("Produto não encontrado.");
    }
    Produto produto = produtoOptional.get();

    // Verifica se o item já está no carrinho
    ItemCarrinho itemExistente = carrinho.getItens().stream()
            .filter(item -> item.getProduto().getSKU().equals(SKU))
            .findFirst()
            .orElse(null);

    if (itemExistente != null) {
        // Se o item já existe, atualiza a quantidade
        itemExistente.setQuantidade(itemExistente.getQuantidade() + quantidade);
    } else {
        // Se não existe, cria um novo item
        ItemCarrinho novoItem = new ItemCarrinho();
        novoItem.setCarrinho(carrinho);
        novoItem.setProduto(produto);
        novoItem.setQuantidade(quantidade);
        carrinho.getItens().add(novoItem);
    }

    return carrinhoRepository.save(carrinho);
}

    // remoção de produtos do carrinho
    public Carrinho removerProduto(Long clienteId, String sku, int quantidade) {
        // Encontrar o carrinho
        Carrinho carrinho = carrinhoRepository.findByClienteId(clienteId);
        
        if (carrinho == null) {
            throw new RuntimeException("Carrinho não encontrado."); // Você pode usar uma exceção mais específica
        }
    
        // Encontrar o item a ser removido
        ItemCarrinho itemRemover = null;
        for (ItemCarrinho item : carrinho.getItens()) {
            if (item.getProduto().getSKU().equals(sku)) {
                itemRemover = item;
                break;
            }
        }
    
        // Se o item não for encontrado, você pode lançar uma exceção ou simplesmente retornar o carrinho
        if (itemRemover == null) {
            throw new RuntimeException("Item não encontrado no carrinho."); // Pode ser uma exceção personalizada
        }
    
        // Verificar se a quantidade a ser removida é menor que a quantidade atual
        if (quantidade >= itemRemover.getQuantidade()) {
            // Remove o item se a quantidade for igual ou maior
            carrinho.getItens().remove(itemRemover);
        } else {
            // Reduz a quantidade do item se a quantidade a ser removida for menor
            itemRemover.setQuantidade(itemRemover.getQuantidade() - quantidade);
        }
    
        // Salva o carrinho atualizado
        return carrinhoRepository.save(carrinho);
    }

    // lista todos os itens do carrinho
    public List<ItemCarrinho> listarItensCarrinho(Long clienteId) {
        // Busca o carrinho do cliente
        Carrinho carrinho = carrinhoRepository.findByClienteId(clienteId);
        
        // Verifica se o carrinho foi encontrado
        if (carrinho == null) {
            throw new EntityNotFoundException("Carrinho não encontrado.");
        }
    
        // Retorna a lista de itens do carrinho
        return carrinho.getItens();
    }



     public Pedido transformarCarrinhoEmPedido(Long clienteId) {
        // Busca o carrinho do cliente
        Carrinho carrinho = carrinhoRepository.findByClienteId(clienteId);
        if (carrinho == null || carrinho.getItens().isEmpty()) {
            throw new RuntimeException("Carrinho vazio ou não encontrado.");
        }

        // Cria um novo pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(carrinho.getCliente());
        pedido.setDataPedido(LocalDateTime.now());

        // Cria a lista de itens do pedido
        Set<ItemPedido> itensPedido = new HashSet<>();
        double totalPedido = 0;

        for (ItemCarrinho itemCarrinho : carrinho.getItens()) {
            // Cria o ItemPedido com base no ItemCarrinho
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(itemCarrinho.getProduto());
            itemPedido.setQuantidade(itemCarrinho.getQuantidade());
            itemPedido.setPreco(itemCarrinho.getProduto().getPreco() * itemCarrinho.getQuantidade());

            // Adiciona o ItemPedido ao Set
            itensPedido.add(itemPedido);

            // Soma ao total do pedido
            totalPedido += itemPedido.getPreco();
        }

        // Associa os itens ao pedido
        pedido.setItens(itensPedido);
        pedido.setTotal(totalPedido);

        // deleta carrinho após transformar em pedido
        carrinhoRepository.delete(carrinho);

        // Salva o pedido (cascading salvará os itens do pedido)
        return pedidoRepository.save(pedido);
    }
}


