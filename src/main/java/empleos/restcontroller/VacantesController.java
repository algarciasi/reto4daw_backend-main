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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import empleos.modelo.dto.VacantesDto;
import empleos.modelo.entity.Categorias;
import empleos.modelo.entity.Empresas;
import empleos.modelo.entity.EstadoVacante;
import empleos.modelo.entity.TipoDestacado;
import empleos.modelo.entity.Vacantes;
import empleos.modelo.service.EmpresasService;
import empleos.modelo.service.SolicitudesService;
import empleos.modelo.service.VacantesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/vacantes")
@Tag(name = "Vacantes", description = "Controlador de Vacantes")
public class VacantesController {
	
	@Autowired
	private VacantesService vserv;
	
	@Autowired
	private EmpresasService eserv;
	
	@Autowired
	private SolicitudesService sserv;
	
	/*@GetMapping("/todas")
	@Operation(summary = "Buscar todas las vacantes")
	public ResponseEntity<List<Vacantes>> listarVacantes() {
        return ResponseEntity.ok(eserv.todasVacantes());
    }*/
	
	
	@GetMapping("/todas")
	@Operation(summary = "Buscar todas las vacantes")
	public ResponseEntity<List<VacantesDto>> getTodasVacantes() {
	    List<Vacantes> lista = vserv.findAll();

	    List<VacantesDto> dtos = lista.stream().map(v -> {
	        VacantesDto dto = new VacantesDto();
	        dto.setIdVacante(v.getIdVacante());
	        dto.setNombre(v.getNombre());
	        dto.setDescripcion(v.getDescripcion());
	        dto.setFecha(v.getFecha()); // ✅
	        dto.setEstatus(v.getEstatus().toString()); // ✅ Si estatus es enum
	        dto.setEmpresa(v.getEmpresa()); // Esto puedes quitarlo si ya haces lo de abajo
	        dto.setSalario(v.getSalario());
	        dto.setDetalles(v.getDetalles());
	        dto.setNombreEmpresa(v.getEmpresa() != null ? v.getEmpresa().getNombre() : "Sin empresa"); // ✅
	        dto.setNombreCategoria(v.getCategoria() != null ? v.getCategoria().getNombre() : ""); // ✅
	        return dto;
	    }).toList();

	    return ResponseEntity.ok(dtos);
	}
	
	/*@PostMapping
	@Operation(summary = "Crear una nueva vacante para una empresa")
	public ResponseEntity<Vacantes> crearVacante(@RequestBody VacantesDto request) {
	    System.out.println("Vacante recibida: " + request.getVacante());
	    System.out.println("Empresa ID: " + request.getIdEmpresa());

	    Vacantes nueva = eserv.nuevaVacantes(request.getVacante(), request.getIdEmpresa());
	    return ResponseEntity.ok(nueva);
	}*/
	
	/*@PostMapping("/nueva")
	public ResponseEntity<?> crearVacante(@Valid @RequestBody VacantesDto vacante) {
	    try {
	        vserv.crearNuevaVacante(vacante);
	        return ResponseEntity.status(HttpStatus.CREATED).build(); // 201 Created
	    } catch (Exception e) {
	        // Aquí puedes agregar log con Logger si lo usas
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(Map.of("error", "Error al crear vacante", "detalle", e.getMessage()));
	    }
	}*/
	
	
	/*@PostMapping("/nueva")
	public ResponseEntity<?> crearVacante(@Valid @RequestBody VacantesDto dto) {
	    try {
	        Vacantes vacante = new Vacantes();

	        // Asignación directa
	        vacante.setIdVacante(dto.getIdVacante());
	        vacante.setNombre(dto.getNombre());
	        vacante.setDescripcion(dto.getDescripcion());
	        vacante.setFecha(dto.getFecha());
	        vacante.setSalario(dto.getSalario());
	        vacante.setDetalles(dto.getDetalles());

	        // Mapeo del Enum EstadoVacante (ej: CREADA, CANCELADA, CUBIERTA)
	        try {
	            vacante.setEstatus(EstadoVacante.valueOf(dto.getEstatus().toUpperCase()));
	        } catch (IllegalArgumentException | NullPointerException e) {
	            return ResponseEntity
	                    .status(HttpStatus.BAD_REQUEST)
	                    .body(Map.of("error", "Estado inválido", "detalle", "Debe ser: CREADA, CANCELADA o CUBIERTA"));
	        }

	        // Mapeo del Enum TipoDestacado (S/N)
	        try {
	            vacante.setDestacado(TipoDestacado.valueOf(dto.getDestacado().toUpperCase()));
	        } catch (IllegalArgumentException | NullPointerException e) {
	            return ResponseEntity
	                    .status(HttpStatus.BAD_REQUEST)
	                    .body(Map.of("error", "Valor 'destacado' inválido", "detalle", "Debe ser: S o N"));
	        }

	        // Empresa viene ya como objeto completo
	       //vacante.setEmpresa(dto.getEmpresa());

	        // Mapeo categoría a partir del idCategoria
	        if (dto.getIdCategoria() != null) {
	            Categorias categoria = new Categorias();
	            categoria.setIdCategoria(dto.getIdCategoria());
	            vacante.setCategoria(categoria);
	        }

	        // Imagen por defecto (puedes personalizar esto)
	        vacante.setImagen("default.png");

	        // Guardar usando servicio
	        vserv.crearNuevaVacante(vacante);

	        return ResponseEntity.status(HttpStatus.CREATED).build(); // 201 Created

	    } catch (Exception e) {
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(Map.of("error", "Error al crear vacante", "detalle", e.getMessage()));
	    }
	}*/
	
	
	@PostMapping("/nueva")
	public ResponseEntity<?> crearVacante(@Valid @RequestBody VacantesDto dto, Authentication authentication) {
	    try {
	        // Obtener empresa desde el email del usuario autenticado
	        String email = authentication.getName();
	        Empresas empresa = eserv.empresaPorEmail(email);

	        if (empresa == null) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                    .body(Map.of("error", "Empresa no encontrada para el usuario autenticado"));
	        }

	        Vacantes vacante = new Vacantes();

	        // Asignación directa
	        vacante.setIdVacante(dto.getIdVacante());
	        vacante.setNombre(dto.getNombre());
	        vacante.setDescripcion(dto.getDescripcion());
	        vacante.setFecha(dto.getFecha());
	        vacante.setSalario(dto.getSalario());
	        vacante.setDetalles(dto.getDetalles());

	        // Enums
	        try {
	            vacante.setEstatus(EstadoVacante.valueOf(dto.getEstatus().toUpperCase()));
	        } catch (IllegalArgumentException | NullPointerException e) {
	            return ResponseEntity
	                    .status(HttpStatus.BAD_REQUEST)
	                    .body(Map.of("error", "Estado inválido", "detalle", "Debe ser: CREADA, CANCELADA o CUBIERTA"));
	        }

	        try {
	            vacante.setDestacado(TipoDestacado.valueOf(dto.getDestacado().toUpperCase()));
	        } catch (IllegalArgumentException | NullPointerException e) {
	            return ResponseEntity
	                    .status(HttpStatus.BAD_REQUEST)
	                    .body(Map.of("error", "Valor 'destacado' inválido", "detalle", "Debe ser: S o N"));
	        }

	        // Asignar empresa
	        vacante.setEmpresa(empresa);

	        // Categoría
	        if (dto.getIdCategoria() != null) {
	            Categorias categoria = new Categorias();
	            categoria.setIdCategoria(dto.getIdCategoria());
	            vacante.setCategoria(categoria);
	        }

	        // Imagen por defecto
	        vacante.setImagen("default.png");

	        // Guardar
	        vserv.crearNuevaVacante(vacante);

	        return ResponseEntity.status(HttpStatus.CREATED).build();

	    } catch (Exception e) {
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(Map.of("error", "Error al crear vacante", "detalle", e.getMessage()));
	    }
	}



	/*@GetMapping("/buscar")
	@Operation(summary = "Buscar vacantes con múltiples filtros")
	public ResponseEntity<List<Vacantes>> buscarVacantesConFiltros(
	        @RequestParam(required = false) String nombre,
	        @RequestParam(required = false) String categoria,
	        @RequestParam(required = false) String pais,
	        @RequestParam(required = false) String fecha,
	        @RequestParam(required = false) Double salario) {

	    List<Vacantes> resultado = vserv.buscarVacantes(nombre, categoria, pais, fecha, salario);
	    return ResponseEntity.ok(resultado);
	}*/
	
	@GetMapping("/buscar")
	@Operation(summary = "Buscar vacantes con múltiples filtros")
	public ResponseEntity<List<VacantesDto>> buscarVacantesConFiltros(
	        @RequestParam(required = false) String nombre,
	        @RequestParam(required = false) String categoria,
	        @RequestParam(required = false) String pais,
	        @RequestParam(required = false) String fecha,
	        @RequestParam(required = false) Double salario) {

	    List<Vacantes> resultado = vserv.buscarVacantes(nombre, categoria, pais, fecha, salario);

	    List<VacantesDto> dtos = resultado.stream().map(v -> {
	        VacantesDto dto = new VacantesDto();
	        dto.setIdVacante(v.getIdVacante());
	        dto.setNombre(v.getNombre());
	        dto.setDescripcion(v.getDescripcion());
	        dto.setFecha(v.getFecha());
	        dto.setEstatus(v.getEstatus() != null ? v.getEstatus().name() : "Sin estado");
	        dto.setSalario(v.getSalario());
	        dto.setDetalles(v.getDetalles());
	        dto.setNombreEmpresa(v.getEmpresa() != null ? v.getEmpresa().getNombre() : "Sin empresa");
	        dto.setNombreCategoria(v.getCategoria() != null ? v.getCategoria().getNombre() : "Sin categoría");
	        dto.setPais(v.getEmpresa() != null ? v.getEmpresa().getPais() : "Desconocido");
	        return dto;
	    }).toList();

	    return ResponseEntity.ok(dtos);
	}

	

	@PutMapping("/actualizar/{id}")
	@Operation(summary = "Actualizar una vacante existente")
	public ResponseEntity<Vacantes> actualizarVacante(
	        @PathVariable int id,
	        @RequestBody Vacantes detalle) {

	    System.out.println("Actualizando vacante ID: " + id);
	    System.out.println("Nuevos datos: " + detalle);

	    Vacantes actualizada = eserv.actualizarVacantes(id, detalle);
	    return ResponseEntity.ok(actualizada);
	}
	
	@DeleteMapping("/cancelar/{id}")
	@Operation(summary = "Cancelar una vacante")
	public ResponseEntity<Void> cancelarVacante(@PathVariable int id) {
	    System.out.println("Cancelando vacante ID: " + id);

	    eserv.cancelarVacantes(id);
	    return ResponseEntity.noContent().build(); // 204 No Content
	}
	
	@PutMapping("/asignar/{idSolicitud}")
	@Operation(summary = "Asignar una vacante a un usuario desde una solicitud")
	public ResponseEntity<Void> asignarVacante(@PathVariable int idSolicitud) {
	    System.out.println("Asignando vacante desde solicitud ID: " + idSolicitud);

	    eserv.asignarVacantes(idSolicitud);
	    return ResponseEntity.noContent().build(); // 204 No Content
	}

	
	@GetMapping("/buscarPorEmail/{email}")
	@Operation(summary = "Obtener vacantes de una empresa por email")
	public ResponseEntity<List<VacantesDto>> obtenerVacantesPorEmail(@PathVariable String email) {
	    Empresas empresa = eserv.empresaPorEmail(email);

	    if (empresa == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body(null); // O puedes devolver ResponseEntity.notFound().build()
	    }

	    List<Vacantes> vacantes = vserv.vacantesPorEmpresa(empresa);

	    List<VacantesDto> dtos = vacantes.stream().map(v -> {
	        VacantesDto dto = new VacantesDto();
	        dto.setIdVacante(v.getIdVacante());
	        dto.setNombre(v.getNombre());
	        dto.setDescripcion(v.getDescripcion());
	        dto.setFecha(v.getFecha());
	        dto.setEstatus(v.getEstatus() != null ? v.getEstatus().name() : "Sin estado");
	        dto.setSalario(v.getSalario());
	        dto.setDetalles(v.getDetalles());
	        dto.setNombreEmpresa(v.getEmpresa() != null ? v.getEmpresa().getNombre() : "Sin empresa");
	        dto.setNombreCategoria(v.getCategoria() != null ? v.getCategoria().getNombre() : "Sin categoría");
	        dto.setPais(v.getEmpresa() != null ? v.getEmpresa().getPais() : "Desconocido");
	        return dto;
	    }).toList();

	    return ResponseEntity.ok(dtos);
	}




	

}
