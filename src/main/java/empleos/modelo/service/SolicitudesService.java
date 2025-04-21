package empleos.modelo.service;

import java.util.List;

import empleos.modelo.entity.Solicitudes;

public interface SolicitudesService {
	Solicitudes enviarSolicitud(int idVacante, String emailUsuario, Solicitudes datos);

	List<Solicitudes> findByUsuario(String email);

	void cancelarSolicitud(int idSolicitud);

	List<Solicitudes> findByEmpresaEmail(String email);
	
	
}
