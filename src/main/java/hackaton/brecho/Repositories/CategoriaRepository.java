package hackaton.brecho.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hackaton.brecho.Models.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
    

    // CREATE

    //Registro de categoria
    @SuppressWarnings("unchecked")
    Categoria save(Categoria categoria);


    // READ

    // Leitura de categorias
    List<Categoria> findAll();

    // Leitura de categoria por nome
    Categoria findByNome(String nome);


    // DELETE

    // Deletar categoria por id
    void deleteById(Long id);
}
