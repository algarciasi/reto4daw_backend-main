package empleos.modelo.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import empleos.modelo.entity.Empresas;
import empleos.modelo.entity.Vacantes;

public interface VacantesRepository extends JpaRepository<Vacantes, Integer>{
	/*@Query("SELECT v FROM Vacantes v WHERE "
		     + "(:nombre IS NULL OR v.nombre LIKE %:nombre%) AND "
		     + "(:categoria IS NULL OR v.categoria.nombre = :categoria) AND "
		     + "(:destacado IS NULL OR v.destacado = :destacado)")
		List<Vacantes> buscarVacantesPorFiltro(
		    @Param("nombre") String nombre,
		    @Param("categoria") String categoria,
		    @Param("destacado") Integer destacado);*/
	@Query("""
		    SELECT v FROM Vacantes v
		    WHERE (:nombre IS NULL OR LOWER(v.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
		      AND (:categoria IS NULL OR LOWER(v.categoria.nombre) LIKE LOWER(CONCAT('%', :categoria, '%')))
		      AND (:pais IS NULL OR LOWER(v.empresa.pais) LIKE LOWER(CONCAT('%', :pais, '%')))
		      AND (:fecha IS NULL OR v.fecha >= :fecha)
		      AND (:salario IS NULL OR v.salario <= :salario)
		""")
		List<Vacantes> buscarPorFiltros(
		    @Param("nombre") String nombre,
		    @Param("categoria") String categoria,
		    @Param("pais") String pais,
		    @Param("fecha") LocalDate fecha,
		    @Param("salario") Double salario
		);



	@EntityGraph(attributePaths = {"empresa", "categoria"})
	List<Vacantes> findByEmpresaIdEmpresa(int idEmpresa);
	
	List<Vacantes> findByEmpresa(Empresas empresa);

}
