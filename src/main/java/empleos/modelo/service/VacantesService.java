package empleos.modelo.service;

import java.util.List;

import empleos.modelo.dto.VacantesDto;
import empleos.modelo.entity.Empresas;
import empleos.modelo.entity.Vacantes;

public interface VacantesService {
	List<Vacantes> buscarVacantes(String nombre, String categoria, String pais, String fecha, Double salario);
	List<Vacantes> findAll();
	List<VacantesDto> obtenerTodasDTOs();
	void crearNuevaVacante(Vacantes vacante);
	List<Vacantes> vacantesPorEmpresa(Empresas empresa);


}
