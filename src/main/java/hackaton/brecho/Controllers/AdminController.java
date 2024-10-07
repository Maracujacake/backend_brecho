package hackaton.brecho.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import hackaton.brecho.Models.Cliente;
import hackaton.brecho.Services.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Administração", description = "Operações de nivel administrativo (Update, Deleção, Leitura de usuários; Adição, Deleção e Alteração de produtos, etc. )")
@RestController
@RequestMapping("/breshow/admin")
public class AdminController {

    @Autowired
    private ClienteService clienteService;
    public AdminController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }


    // READ - R

    // retorna todos os clientes - apenas administrador poderá usar
    @Operation(summary = "Retorna todos os clientes cadastrados")
    @GetMapping(path = "/all")
    public ResponseEntity<List<Cliente>> getAllClientes(){
        return ResponseEntity.ok(clienteService.findAll());
    }

    // retorna info do cliente por ID
    @Operation(summary = "Retorna a informação de um cliente dado seu id")
    @GetMapping(path = "/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id){
        Cliente cliente = clienteService.findById(id);
        if(cliente == null){
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(cliente);
        }
    }


    // UPDATE - U

    // atualiza info do cliente
    @Operation(summary = "Atualiza as informações de um cliente dado seu id")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @RequestBody Cliente updatedCliente) {
        Cliente existingCliente = clienteService.findById(id);
        if (existingCliente == null) {
            return ResponseEntity.notFound().build();
        } else {
            // Atualiza os campos com os dados do cliente enviado
            existingCliente.setPrimeiro_nome(updatedCliente.getPrimeiro_nome());
            existingCliente.setUltimo_nome(updatedCliente.getUltimo_nome());
            existingCliente.setEmail(updatedCliente.getEmail());
            existingCliente.setSenha(updatedCliente.getSenha());
            existingCliente.setTelefone(updatedCliente.getTelefone());
    
            // Salva as alterações
            Cliente savedCliente = clienteService.save(existingCliente);
            return ResponseEntity.ok(savedCliente);
        }
    }



    // DELETE - D

    @Operation(summary = "Deleta o cliente dado seu id")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id){
        Cliente cliente = clienteService.findById(id);
        if(cliente == null){
            return ResponseEntity.notFound().build();
        }
        else{
            clienteService.delete(cliente);
            return ResponseEntity.noContent().build();
        }
    }
}
