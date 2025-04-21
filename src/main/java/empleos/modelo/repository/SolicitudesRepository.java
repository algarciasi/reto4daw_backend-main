package empleos.modelo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import empleos.modelo.entity.Solicitudes;

public interface SolicitudesRepository extends JpaRepository<Solicitudes, Integer>{
    List<Solicitudes> findByVacanteIdVacante(int idVacante);
    List<Solicitudes> findByUsuarioEmail(String email);
    @EntityGraph(attributePaths = {"usuario", "vacante"})
    List<Solicitudes> findAll();
    
    List<Solicitudes> findByVacanteEmpresaUsuarioEmail(String email);



}
