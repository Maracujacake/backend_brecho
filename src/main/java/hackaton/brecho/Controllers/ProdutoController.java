package hackaton.brecho.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;


import hackaton.brecho.Models.Produto;
import hackaton.brecho.Services.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Produto", description = "Operações relacionadas a produtos (registro, leitura, atualização e deleção)")
@RestController
@RequestMapping("/breshow/produto")
public class ProdutoController {
    
    @Autowired
    private ProdutoService produtoService;
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    // CREATE - C

    // registra um novo produto
    @Operation(summary = "Registra um novo produto - somente ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/registrar")
    public ResponseEntity<Produto> registrarProduto(@RequestBody Produto produto) {
        return ResponseEntity.ok(produtoService.save(produto));
    }




    // READ - R

    // retorna todos os produtos
    @Operation(summary = "Lista todos os produtos")
    @GetMapping(path = "/all")
    public ResponseEntity<List<Produto>> getAllProdutos(){
        return ResponseEntity.ok(produtoService.findAll());
    }


    @GetMapping(path = "/categoria/{categoria}")
    public ResponseEntity<List<Produto>> getAllProdutosByCategoria(@PathVariable String categoria){
        return ResponseEntity.ok(produtoService.findByCategoria(categoria));
    }

    // retorna info do produto por SKU
    @Operation(summary = "Retorna a informação de um produto dado seu SKU")
    @GetMapping(path = "/{SKU}")
    public ResponseEntity<Produto> getProdutoBySKU(@PathVariable String SKU){
        Produto produto = produtoService.findBySKU(SKU);
        if(produto == null){
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(produto);
        }
    }



    // UPDATE - U

    // atualiza info do produto
    @Operation(summary = "Atualiza as informações de um produto dado seu SKU - SOMENTE ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/update/{SKU}")
    public ResponseEntity<Produto> updateProduto(@PathVariable String SKU, @RequestBody Produto produto){
        Produto produtoAtualizado = produtoService.findBySKU(SKU);
        if(produtoAtualizado == null){
            return ResponseEntity.notFound().build();
        }
        else{
            produtoAtualizado.setNome(produto.getNome());
            produtoAtualizado.setDescricao(produto.getDescricao());
            produtoAtualizado.setPreco(produto.getPreco());
            produtoAtualizado.setQuantidade(produto.getQuantidade());
            produtoAtualizado.setPreco(produto.getPreco());
            return ResponseEntity.ok(produtoService.saveAndFlush(produtoAtualizado));
        }
    }


    // DELETE - D

    // deleta um produto
    @Operation(summary = "Deleta um produto dado seu SKU - SOMENTE ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/delete/{SKU}")
    public ResponseEntity<Void> deleteProduto(@PathVariable String SKU){
        Produto produto = produtoService.findBySKU(SKU);
        if(produto == null){
            return ResponseEntity.notFound().build();
        }
        else{
            produtoService.deleteBySKU(SKU);
            return ResponseEntity.noContent().build();
        }
    }
}
