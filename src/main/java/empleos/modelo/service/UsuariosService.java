package empleos.modelo.service;

import java.util.List;


import empleos.modelo.entity.Solicitudes;
import empleos.modelo.entity.Usuarios;

public interface UsuariosService {
	
	// apartado B Rol Administrador
	void bajaUsuario(String email);
	Usuarios crearAdministrador(Usuarios usuario);
	List<Usuarios> listadoAdministradores();
	Usuarios actualizarAdministrador(String email, Usuarios datos);
	List<Usuarios> listarTodosUsuarios();
	
	// apartado C Rol Usuario
	Usuarios nuevoUsuario(Usuarios usuario);
	List<Solicitudes> seguimientoSolicitud(String email);
	void cancelarSolicitud(int idSolicitud);
	//Usuarios login(String email, String password); ***********editado 09/04***********
	Usuarios findByEmail(String email);






}
