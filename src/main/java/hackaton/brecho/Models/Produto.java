package hackaton.brecho.Models;


import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "produto_categoria",
        joinColumns = @JoinColumn(name = "produto_id"),
        inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private Set<Categoria> categorias;

    // Getters
    public Long getId() {
        return id;
    }

    public String getSKU() {
        return SKU;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Set<Categoria> getCategorias() {
        return categorias;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setCategorias(Set<Categoria> categorias) {
        this.categorias = categorias;
    }
}
