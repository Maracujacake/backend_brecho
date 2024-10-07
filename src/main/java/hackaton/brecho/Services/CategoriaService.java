package hackaton.brecho.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import hackaton.brecho.Models.Categoria;
import hackaton.brecho.Repositories.CategoriaRepository;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRep;

    public CategoriaService(CategoriaRepository categoriaRep) {
        this.categoriaRep = categoriaRep;
    }

    //CREATE

    public Categoria save(Categoria categoria) {
        return categoriaRep.save(categoria);
    }


    // READ
    public List<Categoria> findAll() {
        return categoriaRep.findAll();
    }

    public Categoria findByNome(String nome) {
        return categoriaRep.findByNome(nome);
    }


    // DELETE
    public void deleteById(Long id) {
        categoriaRep.deleteById(id);
    }
}
