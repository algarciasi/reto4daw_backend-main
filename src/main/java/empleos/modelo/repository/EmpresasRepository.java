package empleos.modelo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import empleos.modelo.entity.Empresas;

public interface EmpresasRepository extends JpaRepository<Empresas, Integer>{
	Optional<Empresas> findByUsuarioEmail(String email);

}
