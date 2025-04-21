package empleos.modelo.service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import empleos.modelo.entity.Empresas;
import empleos.modelo.entity.EstadoVacante;
import empleos.modelo.entity.Solicitudes;
import empleos.modelo.entity.Usuarios;
import empleos.modelo.entity.Vacantes;
import empleos.modelo.repository.EmpresasRepository;
import empleos.modelo.repository.SolicitudesRepository;
import empleos.modelo.repository.UsuariosRepository;
import empleos.modelo.repository.VacantesRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class EmpresasServiceImpl implements EmpresasService {

	@Autowired
	private EmpresasRepository erepo;

	@Autowired
	private SolicitudesRepository srepo;

	@Autowired
	private VacantesRepository vrepo;

	@Autowired
	private UsuariosRepository urepo;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PersistenceContext
    private EntityManager entityManager;

	@Override
	@Transactional
	public Vacantes nuevaVacantes(Vacantes vacante, int idEmpresa) {
	    Empresas empresa = erepo.findById(idEmpresa)
	            .orElseThrow(() -> new RuntimeException("Empresa no encontrada con ID: " + idEmpresa));

	    vacante.setEmpresa(empresa);
	    vacante.setEstatus(EstadoVacante.CREADA);
	    vacante.setFecha(new Date());

	    return vrepo.save(vacante);
	}

	@Override
	public List<Vacantes> todasVacantes() {
		return vrepo.findAll();
	}

	@Override
	public Vacantes actualizarVacantes(int idVacante, Vacantes detalle) {
		Optional<Vacantes> optional = vrepo.findById(idVacante);

		if (optional.isPresent()) {
			Vacantes vacante = optional.get();
			vacante.setNombre(detalle.getNombre());
			vacante.setDescripcion(detalle.getDescripcion());
			vacante.setFecha(detalle.getFecha());
			vacante.setSalario(detalle.getSalario());
			vacante.setDestacado(detalle.getDestacado());
			vacante.setImagen(detalle.getImagen());
			vacante.setDetalles(detalle.getDetalles());
			vacante.setCategoria(detalle.getCategoria());

			return vrepo.save(vacante);
		} else {
			throw new RuntimeException("Vacante no encontrada con ID: " + idVacante);
		}
	}

	@Override
	public void cancelarVacantes(int idVacante) {
		Optional<Vacantes> optional = vrepo.findById(idVacante);
		if (optional.isPresent()) {
			Vacantes vacante = optional.get();
			vacante.setEstatus(EstadoVacante.CANCELADA);
			vrepo.save(vacante);
		} else {
			throw new RuntimeException("No hay vacante con ID: " + idVacante);
		}
	}

	@Override
	public List<Solicitudes> todasSolicitudes() {
		return srepo.findAll();
	}

	@Override
	public void asignarVacantes(int idSolicitud) {
		Optional<Solicitudes> optionalSolicitud = srepo.findById(idSolicitud);

		if (optionalSolicitud.isPresent()) {
			Solicitudes solicitud = optionalSolicitud.get();

			solicitud.setEstado(1);
			srepo.save(solicitud);

			Vacantes vacante = solicitud.getVacante();
			if (vacante != null) {
				vacante.setEstatus(EstadoVacante.CUBIERTA);
				vrepo.save(vacante);
			} else {
				throw new RuntimeException("Vacante no asociada a la solicitud.");
			}

		} else {
			throw new RuntimeException("No hay solicitudes con este ID: " + idSolicitud);
		}
	}

	@Override
	public Vacantes buscarUna(int idVacante) {
		return vrepo.findById(idVacante).orElse(null);
	}

	@Override
	public List<Solicitudes> solicitudesPorVacante(int idVacante) {
		return srepo.findByVacanteIdVacante(idVacante);
	}

	@Override
	public Solicitudes detalleSolicitud(int idSolicitud) {
		return srepo.findById(idSolicitud)
				.orElseThrow(() -> new RuntimeException("No hay solicitudes con este ID: " + idSolicitud));
	}

	@Override
	public Empresas obtenerDatos(int idEmpresa) {
		return erepo.findById(idEmpresa)
				.orElseThrow(() -> new RuntimeException("No hay empresas con este ID: " + idEmpresa));
	}

	@Override
	@Transactional
	public Empresas nuevaEmpresa(Empresas empresa, Usuarios usuario) {
	    if (usuario == null || usuario.getEmail() == null) {
	        throw new RuntimeException("El usuario es obligatorio para registrar una empresa.");
	    }

	    Usuarios usuarioEmpresa;

	    Optional<Usuarios> existente = urepo.findById(usuario.getEmail());

	    if (existente.isPresent()) {
	    	usuarioEmpresa = entityManager.merge(existente.get());
	    } else {
	    	
	    	String password = generarPasswordAleatoria();
	    	System.out.println("Password generada para " + usuario.getEmail() + ": " + password); // <-- Esto
	    	usuario.setPassword(passwordEncoder.encode(password));
	        usuario.setEnabled(1);
	        usuario.setRol("EMPRESA");
	        usuario.setFecha(new Date());

	        usuarioEmpresa = urepo.save(usuario);
	    }

	    empresa.setUsuario(usuarioEmpresa);

	    return erepo.save(empresa);
	}


	private String generarPasswordAleatoria() {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%";
		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			sb.append(chars.charAt(random.nextInt(chars.length())));
		}
		return sb.toString();
	}

	@Override
	@Transactional
	public Empresas editarEmpresa(int idEmpresa, Empresas datos) {
	    Optional<Empresas> optional = erepo.findById(idEmpresa);

	    if (optional.isPresent()) {
	        Empresas empresa = optional.get();

	        // Actualizar campos de empresa
	        empresa.setCif(datos.getCif());
	        empresa.setNombre(datos.getNombre());
	        empresa.setDireccion(datos.getDireccion());
	        empresa.setPais(datos.getPais());

	        // Actualizar datos del usuario
	        Usuarios nuevoUsuario = datos.getUsuario();
	        if (nuevoUsuario != null && nuevoUsuario.getEmail() != null) {
	            Optional<Usuarios> optUsuario = urepo.findById(nuevoUsuario.getEmail());
	            if (optUsuario.isPresent()) {
	                Usuarios editarUsuario = optUsuario.get();
	                editarUsuario.setNombre(nuevoUsuario.getNombre());
	                editarUsuario.setApellidos(nuevoUsuario.getApellidos());
	                urepo.save(editarUsuario);

	                empresa.setUsuario(editarUsuario);
	            } else {
	                throw new RuntimeException("Usuario no encontrado: " + nuevoUsuario.getEmail());
	            }
	        }

	        return erepo.save(empresa);
	    } else {
	        throw new RuntimeException("No hay empresas con este ID: " + idEmpresa);
	    }
	}

	@Override
	@Transactional
	public void eliminarEmpresa(int idEmpresa) {
	    Empresas empresa = erepo.findById(idEmpresa)
	        .orElseThrow(() -> new RuntimeException("Empresa no encontrada con ID: " + idEmpresa));

	    // Eliminar las vacantes asociadas
	    List<Vacantes> vacantes = vrepo.findByEmpresaIdEmpresa(idEmpresa);
	    if (!vacantes.isEmpty()) {
	        System.out.println("Eliminando " + vacantes.size() + " vacantes de la empresa...");
	        vrepo.deleteAll(vacantes);
	    }

	    // Elimina la empresa
	    erepo.deleteById(idEmpresa);

	    System.out.println("Empresa con ID " + idEmpresa + " eliminada correctamente.");
	}



	@Override
	public List<Empresas> todasEmpresas() {
		return erepo.findAll();
	}
	
	@Override
	public Empresas empresaPorEmail(String email) {
	    return erepo.findByUsuarioEmail(email)
	        .orElseThrow(() -> new RuntimeException("Empresa no encontrada para el email: " + email));
	}


}
