package empleos.modelo.service;

import java.util.List;

import empleos.modelo.entity.Empresas;
import empleos.modelo.entity.Solicitudes;
import empleos.modelo.entity.Usuarios;
import empleos.modelo.entity.Vacantes;

public interface EmpresasService {
	
	// apartado A "Rol Empresas"
	Vacantes nuevaVacantes(Vacantes vacante, int idEmpresa);
	List<Vacantes> todasVacantes();
	Vacantes buscarUna(int idVacante);
	Vacantes actualizarVacantes(int idVacante, Vacantes detalle);
	void cancelarVacantes(int idVacante);
	List<Solicitudes> todasSolicitudes();
	void asignarVacantes(int idSolicitud);
	List<Solicitudes> solicitudesPorVacante(int idVacante); 
	Solicitudes detalleSolicitud(int idSolicitud);
	Empresas obtenerDatos(int idEmpresa); // compartido entre Rol Empresas y Administrador
	List<Empresas> todasEmpresas();
	Empresas empresaPorEmail(String email);

	// apartado B "Rol Administrador"
	Empresas nuevaEmpresa(Empresas empresa, Usuarios usuario);
	Empresas editarEmpresa(int idEmpresa, Empresas datos);
	void eliminarEmpresa(int idEmpresa);
	

}
