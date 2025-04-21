package empleos.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import empleos.modelo.dto.EmpresasDto;
import empleos.modelo.entity.Empresas;
import empleos.modelo.service.EmpresasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/empresas")
@Tag(name = "Empresas", description = "Controlador de Empresas")
public class EmpresasController {
	
	@Autowired
	private EmpresasService eserv;
	
	@PostMapping("/nueva")
	@Operation(summary = "Nueva Empresa")
	public ResponseEntity<Empresas> crearEmpresa(@RequestBody EmpresasDto request) {
		System.out.println("Empresa recibida: " + request.getEmpresa());
	    System.out.println("Usuario recibido: " + request.getUsuario());
	    
	    Empresas nueva = eserv.nuevaEmpresa(request.getEmpresa(), request.getUsuario());
	    return ResponseEntity.ok(nueva);
	}

	
	@Operation(summary = "Listar todas las empresas")
    @GetMapping("/todas")
    public ResponseEntity<List<Empresas>> listarEmpresas() {
        return ResponseEntity.ok(eserv.todasEmpresas());
    }
	
	@Operation(summary = "Eliminar una empresa")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarEmpresa(@PathVariable int id) {
        eserv.eliminarEmpresa(id);
        return ResponseEntity.noContent().build();
    }
	
	@Operation(summary = "Buscar una empresa por ID")
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Empresas> obtenerEmpresa(@PathVariable int id) {
        Empresas empresa = eserv.obtenerDatos(id);
        return ResponseEntity.ok(empresa);
    }

	@PutMapping("/editar/{id}")
	@Operation(summary = "Editar una empresa existente")
	public ResponseEntity<Empresas> editarEmpresa(
	        @PathVariable int id,
	        @RequestBody EmpresasDto request) {

	   /* System.out.println("Editando empresa ID: " + id);
	    System.out.println("Nuevos datos empresa: " + request.getEmpresa());
	    System.out.println("Nuevos datos usuario (opcional): " + request.getUsuario());
       */
	    // Asignacion manual del usuario
	    Empresas datos = request.getEmpresa();
	    if (datos != null && request.getUsuario() != null) {
	        datos.setUsuario(request.getUsuario());
	    }

	    Empresas actualizada = eserv.editarEmpresa(id, datos);
	    return ResponseEntity.ok(actualizada);
	}
	
	@PutMapping("/asignar/{idSolicitud}")
	public ResponseEntity<String> asignarVacante(@PathVariable int idSolicitud) {
	    eserv.asignarVacantes(idSolicitud);
	    return ResponseEntity.ok("Vacante asignada correctamente.");
	}


	@Operation(summary = "Obtener empresa por email del usuario")
	@GetMapping("/buscar-por-email/{email}")
	public ResponseEntity<Empresas> buscarPorEmail(@PathVariable String email) {
	    Empresas empresa = eserv.empresaPorEmail(email);
	    if (empresa == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok(empresa);
	}

	
	

}
