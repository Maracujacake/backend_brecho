package hackaton.brecho.Controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import hackaton.brecho.Models.Cliente;
import hackaton.brecho.Models.Usuario;
import hackaton.brecho.Services.ClienteService;
import hackaton.brecho.Services.UsuarioService;
import hackaton.brecho.config.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cliente", description = "Operações relacionadas ao cliente (registro, leitura, atualização e deleção DOS PRÓPRIOS DADOS)")
@RestController
@RequestMapping("/breshow/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Autowired
    private UsuarioService usuarioService;

    //CREATE - C

    // registra um novo cliente
    @Operation(summary = "Registra um novo cliente")
    @PostMapping("/register")
    public ResponseEntity<Cliente> registerCliente(@RequestBody Cliente clienteInfo) {

        if (usuarioService.findByEmail(clienteInfo.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // Conflito, email já existe
        }

        // Criação do Cliente
        Cliente cliente = new Cliente();
        cliente.setEmail(clienteInfo.getEmail());
        cliente.setSenha(clienteInfo.getSenha());
        cliente.setPrimeiro_nome(clienteInfo.getPrimeiro_nome()); // Preenche os campos do cliente
        cliente.setUltimo_nome(clienteInfo.getUltimo_nome());
        cliente.setTelefone(clienteInfo.getTelefone());
    
        clienteService.save(cliente); // Salva o cliente

        // Criação do Usuário
        Usuario usuario = new Usuario();
        usuario.setEmail(cliente.getEmail());
        usuario.setPassword(cliente.getSenha());
        usuario.setRoles(Set.of("ROLE_USER")); // Definindo o papel do usuário

        usuarioService.save(usuario); // Salva o usuário
    
        return ResponseEntity.ok(cliente); // Retorna o cliente salvo
    }

    // READ - R
    @Operation(summary = "Retorna informação do cliente autenticado")
    @GetMapping("/me")
    public ResponseEntity<Cliente> getMeuCliente() {
        // Pega o usuário autenticado do SecurityContext
        System.out.println("FLOEDFKSEDOIFJOIAFJMERSWIOJNWERIOUFJWERIOFJWSERIOFAWJERSOIFJWEIRODFJREWIOD");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // Verifica se o usuário está autenticado
        if (authentication == null || !authentication.isAuthenticated() || authentication.getName().equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Usuário não autenticado
        }
    
        String email = authentication.getName(); // Recupera o email
        System.out.println("Email autenticado: " + email);
    
        // Busca o cliente pelo email
        Cliente cliente = clienteService.findByEmail(email);
        if (cliente == null) {
            return ResponseEntity.notFound().build(); // Retorna 404 se o cliente não for encontrado
        } else {
            return ResponseEntity.ok(cliente); // Retorna os dados do cliente autenticado
        }
    }

    // UPDATE - U

    // atualiza info do cliente
    @Operation(summary = "Atualiza as informações do cliente autenticado")
    @PutMapping(path = "/update")
    public ResponseEntity<Cliente> updateCliente(@RequestBody Cliente updatedCliente) {
        // Obtém o Authentication do contexto de segurança
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // Obtém o email do cliente autenticado
        String authenticatedEmail = userDetails.getUsername();

        // Busca o cliente pelo email
        Cliente existingCliente = clienteService.findByEmail(authenticatedEmail);
        if (existingCliente == null) {
            return ResponseEntity.notFound().build();
        } else {
            // Atualiza os campos com os dados do cliente enviado
            if(updatedCliente.getPrimeiro_nome() != null)
                existingCliente.setPrimeiro_nome(updatedCliente.getPrimeiro_nome());
            
            if(updatedCliente.getUltimo_nome() != null)
                existingCliente.setUltimo_nome(updatedCliente.getUltimo_nome());

            if(updatedCliente.getEmail() != null)    
                existingCliente.setEmail(updatedCliente.getEmail());

            if(updatedCliente.getSenha() != null)    
                existingCliente.setSenha(updatedCliente.getSenha());

            if(updatedCliente.getTelefone() != null)
                existingCliente.setTelefone(updatedCliente.getTelefone());

            // Salva as alterações
            Cliente savedCliente = clienteService.save(existingCliente);
            return ResponseEntity.ok(savedCliente);
        }
    }



    // DELETE - D

    // deleta um cliente - somente a própria conta
    @Operation(summary = "Deleta o cliente autenticado")
    @DeleteMapping(path = "/delete")
    public ResponseEntity<Void> deleteCliente() {
        // Obtém o Authentication do contexto de segurança
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
    
        // Obtém o email do cliente autenticado
        String authenticatedEmail = userDetails.getUsername(); // Supondo que o username é o email
    
        // Busca o cliente pelo email
        Cliente cliente = clienteService.findByEmail(authenticatedEmail);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        } else {
            clienteService.delete(cliente);
            return ResponseEntity.noContent().build();
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        // Limpa o contexto de segurança, invalidando efetivamente a sessão
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();
    }
    
}
