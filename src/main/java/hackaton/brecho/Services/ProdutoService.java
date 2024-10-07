package hackaton.brecho.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hackaton.brecho.Models.Categoria;
import hackaton.brecho.Models.Produto;
import hackaton.brecho.Repositories.CategoriaRepository;
import hackaton.brecho.Repositories.ProdutoRepository;

@Service
public class ProdutoService {
    
    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    public ProdutoService(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public Produto save(Produto prod) {
       System.out.println(prod.getNome()); 
        return produtoRepository.save(prod);
    }

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }


    public List<Produto> findByCategoria(String categoriaNome) {
        Categoria categoria = categoriaRepository.findByNome(categoriaNome);
        return produtoRepository.findByCategorias(categoria);
    }


    public Produto findBySKU(String SKU) {
        return produtoRepository.findBySKU(SKU).orElse(null);
    }

    public Produto saveAndFlush(Produto prod) {
        return produtoRepository.saveAndFlush(prod);
    }

    public void deleteBySKU(String SKU) {
        produtoRepository.deleteBySKU(SKU);
    }

    public Produto adicionarCategoriaAoProduto(String SKU, String nome) {
        // Busca o produto pelo ID
        Produto produto = produtoRepository.findBySKU(SKU)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Busca a categoria pelo ID
        Categoria categoria = categoriaRepository.findByNome(nome);

        if (categoria == null) {
            throw new RuntimeException("Categoria não encontrada");
        }

        // Adiciona a categoria ao produto
        produto.getCategorias().add(categoria);

        // Salva o produto atualizado
        return produtoRepository.save(produto);
    }
}
