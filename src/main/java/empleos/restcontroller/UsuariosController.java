package empleos.restcontroller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import empleos.modelo.entity.Solicitudes;
import empleos.modelo.entity.Usuarios;
import empleos.modelo.service.SolicitudesService;
import empleos.modelo.service.UsuariosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Controlador de Usuarios")
public class UsuariosController {
	
	@Autowired
	private UsuariosService userv;
	
	@Autowired
	private SolicitudesService sserv;
	
	@GetMapping("/seguimiento/{email}")
	@Operation(summary = "Seguimiento solicitud")
	public ResponseEntity<List<Solicitudes>> seguimientoSolicitudes(@PathVariable String email) {
	    List<Solicitudes> solicitudes = userv.seguimientoSolicitud(email);
	    return ResponseEntity.ok(solicitudes);
	}
	
	@DeleteMapping("/cancelar/{idSolicitud}")
	@Operation(summary = "Cancelar solicitud")	
	public ResponseEntity<String> cancelarSolicitud(@PathVariable int idSolicitud) {
	    try {
	        userv.cancelarSolicitud(idSolicitud);
	        return ResponseEntity.ok("Solicitud cancelada correctamente.");
	    } catch (RuntimeException e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    }
	}
	
	@PostMapping("/registro")
	@Operation(summary = "Registro de usuario")
	public ResponseEntity<Usuarios> altaUsuario(@RequestBody Usuarios usuario) {
	    try {
	        Usuarios nuevo = userv.nuevoUsuario(usuario);
	        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
	    } catch (RuntimeException e) {
	        return ResponseEntity.badRequest().build();
	    }
	}
	
	@PostMapping("/registro-admon")
	@Operation(summary = "Registro de administrador")
	public ResponseEntity<Usuarios> altaAdmin(@RequestBody Usuarios usuario) {
	    try {
	        return new ResponseEntity<>(userv.crearAdministrador(usuario), HttpStatus.CREATED);
	    } catch (RuntimeException e) {
	        return ResponseEntity.badRequest().build();
	    }
	}
	
	@DeleteMapping("/baja/{email}")
	@Operation(summary = "Baja de usuario")
	public ResponseEntity<Map<String, String>> bajaUsuario(@PathVariable String email) {
	    try {
	        userv.bajaUsuario(email);
	        return ResponseEntity.ok(Map.of("mensaje", "Usuario desactivado correctamente"));
	    } catch (RuntimeException ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
	    }
	}

	
	@GetMapping("/todos")
	@Operation(summary = "Ver usuarios")
    public ResponseEntity<List<Usuarios>> listarTodosUsuarios() {
        List<Usuarios> usuarios = userv.listarTodosUsuarios();
        return ResponseEntity.ok(usuarios);
    }
	
	@GetMapping("/debug/yo")
	public ResponseEntity<String> usuarioActual(Authentication auth) {
	    if (auth == null) return ResponseEntity.status(401).body("No autenticado");
	    return ResponseEntity.ok("Autenticado como: " + auth.getName() + " | Roles: " + auth.getAuthorities());
	}
	
	
	
}
