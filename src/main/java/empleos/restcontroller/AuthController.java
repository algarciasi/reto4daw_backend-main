package empleos.restcontroller;



import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import empleos.modelo.dto.LoginRequest;
import empleos.modelo.entity.Usuarios;
import empleos.modelo.service.UsuariosService;
import org.springframework.security.core.Authentication;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")

public class AuthController {
	

    @Autowired
    private UsuariosService userv;

      
    //***********editado 09/04*********** funcionando ok registro
    /*@GetMapping("/login") // Cambiamos a GetMapping
    public ResponseEntity<?> login(Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Login exitoso");
            response.put("username", authentication.getName()); // Obtener el username
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("No autenticado"); // Debería ser manejado por Spring Security
        }
    }*/
    
    @GetMapping("/login")
    public ResponseEntity<?> login(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Usuarios usuario = userv.findByEmail(authentication.getName());

            Map<String, String> response = new HashMap<>();
            response.put("message", "Login exitoso");
            response.put("nombre", usuario.getNombre());
            response.put("username", usuario.getEmail());
            response.put("rol", usuario.getRol()); 

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("No autenticado");
        }
    }


}
