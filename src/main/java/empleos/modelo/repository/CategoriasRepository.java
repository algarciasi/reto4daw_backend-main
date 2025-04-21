package empleos.modelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import empleos.modelo.entity.Categorias;

public interface CategoriasRepository extends JpaRepository<Categorias, Integer>{

}
