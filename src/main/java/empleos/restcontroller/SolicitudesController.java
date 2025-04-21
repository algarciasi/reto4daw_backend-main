package empleos.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import empleos.modelo.dto.SolicitudRespuestaDto;
import empleos.modelo.dto.SolicitudesDto;
import empleos.modelo.entity.Solicitudes;
import empleos.modelo.service.EmpresasService;
import empleos.modelo.service.SolicitudesService;
import empleos.modelo.service.VacantesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/solicitudes")
@Tag(name = "Solicitudes", description = "Controlador de Solicitudes")
public class SolicitudesController {
	
	@Autowired
	private VacantesService vserv;
	
	@Autowired
	private EmpresasService eserv;
	
	@Autowired
	private SolicitudesService sserv;
	
	
	/*@GetMapping("/todas")
	@Operation(summary = "Buscar todas las solicitudes")
	public ResponseEntity<List<Solicitudes>> listarSolicitudes() {
        return ResponseEntity.ok(eserv.todasSolicitudes());
    }*/
	
	@GetMapping("/todas")
	public ResponseEntity<List<SolicitudRespuestaDto>> listarSolicitudes() {
	    List<Solicitudes> solicitudes = eserv.todasSolicitudes();

	    List<SolicitudRespuestaDto> dtos = solicitudes.stream().map(sol -> {
	        SolicitudRespuestaDto dto = new SolicitudRespuestaDto();
	        dto.setIdSolicitud(sol.getIdSolicitud());
	        dto.setFecha(sol.getFecha().toString()); 
	        //dto.setArchivo(sol.getArchivo());
	        dto.setComentarios(sol.getComentarios());
	        dto.setEstado(sol.getEstado());
	        dto.setCurriculum(sol.getCurriculum());
	        dto.setNombreVacante(sol.getVacante().getNombre());
	        dto.setEmailUsuario(sol.getUsuario().getEmail());
	        return dto;
	    }).toList();

	    return ResponseEntity.ok(dtos);
	}

	
	
	@PostMapping("/nueva")
	@Operation(summary = "Enviar solicitud de empleo a una vacante")
	public ResponseEntity<Solicitudes> enviarSolicitud(@RequestBody SolicitudesDto request) {
	    System.out.println("Enviando solicitud para vacante ID: " + request.getIdVacante());
	    System.out.println("Usuario: " + request.getEmailUsuario());

	    Solicitudes creada = sserv.enviarSolicitud(
	        request.getIdVacante(),
	        request.getEmailUsuario(),
	        request.getSolicitud()
	    );

	    return ResponseEntity.ok(creada);
	}
	
	/*@GetMapping("/usuario")
	@Operation(summary = "Obtener solicitudes de un usuario por email")
	public ResponseEntity<List<Solicitudes>> getSolicitudesPorUsuario(@RequestParam String email) {
	    return ResponseEntity.ok(sserv.findByUsuario(email));
	}*/
	
	@GetMapping("/usuario")
	public List<SolicitudRespuestaDto> solicitudesPorUsuario(@RequestParam String email) {
	    List<Solicitudes> solicitudes = sserv.findByUsuario(email);

	    return solicitudes.stream().map(s -> {
	        SolicitudRespuestaDto dto = new SolicitudRespuestaDto();
	        dto.setIdSolicitud(s.getIdSolicitud());
	        dto.setFecha(s.getFecha().toString());
	        dto.setComentarios(s.getComentarios());
	        dto.setEstado(s.getEstado());
	        dto.setCurriculum(s.getCurriculum());
	        dto.setEmailUsuario(s.getUsuario().getEmail());

	        if (s.getVacante() != null) {
	            dto.setNombreVacante(s.getVacante().getNombre());
	            if (s.getVacante().getEmpresa() != null) {
	                dto.setNombreEmpresa(s.getVacante().getEmpresa().getNombre());
	            }
	            if (s.getVacante().getCategoria() != null) {
	                dto.setNombreCategoria(s.getVacante().getCategoria().getNombre());
	            }
	        }

	        return dto;
	    }).toList();
	}

	@DeleteMapping("/cancelar/{idSolicitud}")
	@Operation(summary = "Cancelar solicitud")	
	public ResponseEntity<String> cancelarSolicitud(@PathVariable int idSolicitud) {
	    try {
	        sserv.cancelarSolicitud(idSolicitud);
	        return ResponseEntity.ok("Solicitud cancelada correctamente.");
	    } catch (RuntimeException e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    }
	}


	
	@GetMapping("/empresa")
	public List<SolicitudRespuestaDto> solicitudesPorEmpresa(@RequestParam String email) {
	    List<Solicitudes> solicitudes = sserv.findByEmpresaEmail(email);

	    return solicitudes.stream().map(s -> {
	        SolicitudRespuestaDto dto = new SolicitudRespuestaDto();
	        dto.setIdSolicitud(s.getIdSolicitud());
	        dto.setFecha(s.getFecha().toString());
	        dto.setComentarios(s.getComentarios());
	        dto.setEstado(s.getEstado());
	        dto.setCurriculum(s.getCurriculum());
	        dto.setEmailUsuario(s.getUsuario().getEmail());

	        if (s.getVacante() != null) {
	            dto.setNombreVacante(s.getVacante().getNombre());
	            if (s.getVacante().getEmpresa() != null) {
	                dto.setNombreEmpresa(s.getVacante().getEmpresa().getNombre());
	            }
	            if (s.getVacante().getCategoria() != null) {
	                dto.setNombreCategoria(s.getVacante().getCategoria().getNombre());
	            }
	        }

	        return dto;
	    }).toList();
	}



}
