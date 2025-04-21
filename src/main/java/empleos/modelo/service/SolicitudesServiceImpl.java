package empleos.modelo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import empleos.modelo.entity.Solicitudes;
import empleos.modelo.entity.Usuarios;
import empleos.modelo.entity.Vacantes;
import empleos.modelo.repository.SolicitudesRepository;
import empleos.modelo.repository.UsuariosRepository;
import empleos.modelo.repository.VacantesRepository;

@Service
public class SolicitudesServiceImpl implements SolicitudesService{
	
	@Autowired
	private SolicitudesRepository srepo;
	
	@Autowired
	private VacantesRepository vrepo;
	
	@Autowired
	private UsuariosRepository urepo;

	@Override
	public Solicitudes enviarSolicitud(int idVacante, String emailUsuario, Solicitudes datos) {
		Vacantes vacante = vrepo.findById(idVacante)
		        .orElseThrow(() -> new RuntimeException("Vacante no encontrada"));

		    Usuarios usuario = urepo.findById(emailUsuario)
		        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		    datos.setVacante(vacante);
		    datos.setUsuario(usuario);
		    datos.setFecha(new Date());
		    datos.setEstado(0); 
		    return srepo.save(datos);
	}

	@Override
	public List<Solicitudes> findByUsuario(String email) {
		return srepo.findByUsuarioEmail(email);
	}
	
	
	@Override
	public void cancelarSolicitud(int idSolicitud) {
		Solicitudes solicitud = srepo.findById(idSolicitud)
		        .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
		    
		    if (solicitud.getEstado() == 1) {
		        throw new RuntimeException("No se puede cancelar una solicitud ya asignada");
		    }
		    
		    srepo.deleteById(idSolicitud);
		
	}

	
	public List<Solicitudes> findByEmpresaEmail(String email) {
		return srepo.findByVacanteEmpresaUsuarioEmail(email);
	}

}
