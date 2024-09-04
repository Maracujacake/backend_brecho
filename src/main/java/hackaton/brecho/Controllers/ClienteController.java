package hackaton.brecho.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import hackaton.brecho.Models.Cliente;
import hackaton.brecho.Services.ClienteService;

@RestController
@RequestMapping("/breshow/cliente")
public class ClienteController {
    private final ClienteService clienteService;
    
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // retorna todos os clientes - apenas administrador
    @GetMapping(path = "/all")
    public ResponseEntity<List<Cliente>> getAllClientes(){
        return ResponseEntity.ok(clienteService.findAll());
    }

    // retorna info do cliente por ID
    @GetMapping(path = "/{id}")
    public ResponseEntity<Cliente> getClienteById(Long id){
        Cliente cliente = clienteService.findById(id);
        if(cliente == null){
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(cliente);
        }
    }

    // registra um novo cliente
    @PostMapping(path = "/register")
    public ResponseEntity<Cliente> registerCliente(Cliente cliente){
        return ResponseEntity.ok(clienteService.save(cliente));
    }

    // atualiza info do cliente
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<Cliente> updateCliente(Long id){
        Cliente cliente = clienteService.findById(id);
        if(cliente == null){
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(clienteService.save(cliente));
        }
    }

    // deleta um cliente - somente a própria conta
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> deleteCliente(Long id){
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
