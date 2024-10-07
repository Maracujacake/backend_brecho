package hackaton.brecho.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hackaton.brecho.Models.Categoria;
import hackaton.brecho.Services.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Categoria", description = "Operações relacionadas a categorias de produtos, devem ser criadas antes dos produtos serem inseridos")
@RestController
@RequestMapping("/breshow/categoria")
public class CategoriaController {
    
    @Autowired
    private CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }


    //CREATE - C
    @Operation(summary = "Registra uma nova categoria - somente ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/register")
    public ResponseEntity<Categoria> registerCategoria(@RequestBody Categoria categoria) {
        return ResponseEntity.ok(categoriaService.save(categoria));
    }


    // READ - R
    @Operation(summary = "Lista todas as categorias")
    @GetMapping(path = "/all")
    public ResponseEntity<List<Categoria>> getAllCategorias(){
        return ResponseEntity.ok(categoriaService.findAll());
    }

    @Operation(summary = "Pega uma categoria por nome")
    @GetMapping(path = "/{nome}")
    public ResponseEntity<Categoria> getCategoriaByName(String nome){
        Categoria categoria = categoriaService.findByNome(nome);
        if(categoria == null){
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(categoria);
        }
    }


    // DELETE - D
    @Operation(summary = "Deleta uma categoria por ID - somente ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> deleteCategoriaById(Long id){
        categoriaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
