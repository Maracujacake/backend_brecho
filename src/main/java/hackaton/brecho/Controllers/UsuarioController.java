package hackaton.brecho.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import hackaton.brecho.Models.LoginRequest;
import hackaton.brecho.config.JwtUtil;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Usuario", description = "Operações relacionadas a usuários (login) - Diferente de cliente")
@RestController
@RequestMapping("/breshow/usuario")
public class UsuarioController {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private JwtUtil jwtUtil; // Injeção do JwtUtil
    

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
    
            // Autenticação bem-sucedida, armazena no SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);
    
            // Gerar token JWT
            String token = jwtUtil.generateToken(loginRequest.getEmail());
            return ResponseEntity.ok(token); // Retorna o token no corpo da resposta
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }
    
    
}
