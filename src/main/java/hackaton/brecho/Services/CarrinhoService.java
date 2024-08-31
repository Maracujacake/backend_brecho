package hackaton.brecho.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hackaton.brecho.Models.Carrinho;
import hackaton.brecho.Models.ItemCarrinho;
import hackaton.brecho.Models.Produto;
import hackaton.brecho.Repositories.CarrinhoRepository;
import hackaton.brecho.Repositories.ProdutoRepository;

@Service
public class CarrinhoService {
    private final CarrinhoRepository carrinhoRepository;
    private final ProdutoRepository produtoRepository;

    @Autowired
    public CarrinhoService(CarrinhoRepository carrinhoRepository, ProdutoRepository produtoRepository) {
        this.carrinhoRepository = carrinhoRepository;
        this.produtoRepository = produtoRepository;
    }

    public Carrinho adicionarProduto(int quantidade, Long clienteId, Long carrinhoId, Long produtoId) {
        Carrinho carrinho = carrinhoRepository.findByClienteIdAndCarrinhoId(clienteId, carrinhoId);
        
        Optional<Produto> produto = produtoRepository.findById(produtoId);

        ItemCarrinho novoItem = new ItemCarrinho();
        novoItem.setCarrinho(carrinho);
        novoItem.setProduto(produto);
        novoItem.setQuantidade(quantidade);
        carrinho.getItens().add(novoItem);

        return carrinhoRepository.save(carrinho);
    }
}


