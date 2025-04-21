package empleos.modelo.service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import empleos.modelo.entity.Solicitudes;
import empleos.modelo.entity.Usuarios;
import empleos.modelo.repository.SolicitudesRepository;
import empleos.modelo.repository.UsuariosRepository;

@Service
public class UsuariosServiceImpl implements UsuariosService{
	
	@Autowired
	private UsuariosRepository urepo;
	
	@Autowired
	private SolicitudesRepository srepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	

	@Override
	public void bajaUsuario(String email) {
		 Usuarios usuario = urepo.findById(email)
			        .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + email));
			    usuario.setEnabled(0);
			    urepo.save(usuario);		
	}

	@Override
	public Usuarios crearAdministrador(Usuarios usuario) {
		String password = generarPasswordAleatoria();
	    usuario.setPassword(passwordEncoder.encode(password));
	    usuario.setEnabled(1);
	    usuario.setRol("ADMON");
	    return urepo.save(usuario);
	}

	@Override
	public List<Usuarios> listadoAdministradores() {
		return urepo.findByRol("ADMON");
	}

	@Override
	public Usuarios actualizarAdministrador(String email, Usuarios datos) {
		return urepo.findById(email)
		        .map(u -> {
		            u.setNombre(datos.getNombre());
		            u.setApellidos(datos.getApellidos());
		            u.setPassword(passwordEncoder.encode(datos.getPassword()));
		            return urepo.save(u);
		        })
		        .orElseThrow(() -> new RuntimeException("Administrador no encontrado: " + email));
	}

	@Override
	public List<Usuarios> listarTodosUsuarios() {
		return urepo.findAll();
	}
	
	/*private String generarPasswordAleatoria() {
	    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%";
	    SecureRandom random = new SecureRandom();
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < 10; i++) {
	        sb.append(chars.charAt(random.nextInt(chars.length())));
	    }
	    return sb.toString();
	}*/

	/*@Override
	public Usuarios nuevoUsuario(Usuarios usuario) {
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
	    usuario.setRol("CLIENTE");
	    usuario.setEnabled(1);
	    usuario.setFecha(new Date());
	    return urepo.save(usuario);
	}*/
	
	public String generarPasswordAleatoria() {
	    return UUID.randomUUID().toString().substring(0, 8); // ejemplo simple: "a3f7e2c1"
	}
	
	@Override
	public Usuarios nuevoUsuario(Usuarios usuario) {
	    if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
	        System.out.println("Password inicial para " + usuario.getPassword());
	        String claveGenerada = generarPasswordAleatoria();
	        System.out.println("Password generado para " + usuario.getEmail() + ": " + claveGenerada);
	        usuario.setPassword(claveGenerada);
	        
	    }

	    usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
	    usuario.setRol("CLIENTE");
	    usuario.setEnabled(1);
	    usuario.setFecha(new Date());
	    return urepo.save(usuario);
	}


	@Override
	public List<Solicitudes> seguimientoSolicitud(String email) {
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

	@Override
	public Usuarios findByEmail(String email) {
		return urepo.findByEmail(email);
	}
	
	
	//***********editado 09/04***********
	/*@Override
	public Usuarios login(String email, String password) {
	    return urepo.findById(email)
	        .filter(usuario -> {
	            System.out.println("Intentando login...");
	            System.out.println("Email: " + email);
	            System.out.println("Password ingresado: " + password);
	            System.out.println("Password BBDD: " + usuario.getPassword());

	            boolean match = passwordEncoder.matches(password, usuario.getPassword());
	            System.out.println("¿Coincide? " + match);

	            return match;
	        })
	        .orElseThrow(() -> new RuntimeException("Credenciales incorrectas"));
	}*/



	
}
