package empleos.modelo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import empleos.modelo.entity.Usuarios;

public interface UsuariosRepository extends JpaRepository<Usuarios, String>{
	List<Usuarios> findByRol(String rol);
	Usuarios findByEmail(String email);

}
