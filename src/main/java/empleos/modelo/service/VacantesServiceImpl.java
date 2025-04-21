package empleos.modelo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import empleos.modelo.entity.Categorias;
import empleos.modelo.entity.Empresas;
import empleos.modelo.entity.EstadoVacante;
import empleos.modelo.entity.TipoDestacado;
import empleos.modelo.dto.VacantesDto;
import empleos.modelo.entity.Vacantes;
import empleos.modelo.repository.CategoriasRepository;
import empleos.modelo.repository.VacantesRepository;

@Service
public class VacantesServiceImpl implements VacantesService{
	
	@Autowired
	private VacantesRepository vrepo;
	
	@Autowired
	private CategoriasRepository crepo;

	/*@Override
	public List<Vacantes> buscarVacantes(String empresa, String tipoContrato, Integer idCategoria) {
	    return vrepo.buscarVacantesPorFiltro(
	        empresa == null ? "" : empresa,
	        tipoContrato == null ? "" : tipoContrato,
	        idCategoria
	    );
	}*/
	
	

	@Override
	public List<Vacantes> findAll() {
	    return vrepo.findAll();
	}
	
	@Override
	public List<VacantesDto> obtenerTodasDTOs() {
	    return vrepo.findAll().stream().map(v -> {
	        VacantesDto dto = new VacantesDto();
	        dto.setIdVacante(v.getIdVacante());
	        dto.setNombre(v.getNombre());
	        dto.setDescripcion(v.getDescripcion());
	        dto.setFecha(v.getFecha());
	        dto.setEstatus(v.getEstatus() != null ? v.getEstatus().name() : "Sin estado");
	        dto.setSalario(v.getSalario());
	        dto.setEmpresa(v.getEmpresa());
	        dto.setNombreEmpresa(v.getEmpresa() != null ? v.getEmpresa().getNombre() : "Sin empresa");
	        dto.setNombreCategoria(v.getCategoria() != null ? v.getCategoria().getNombre() : "Sin categoría");
	        return dto;
	    }).toList();
	}

	@Override
	public void crearNuevaVacante(Vacantes vacante) {
	   vrepo.save(vacante);
	}

	@Override
	public List<Vacantes> buscarVacantes(String nombre, String categoria, String pais, String fechaStr, Double salario) {
	    nombre = (nombre != null && !nombre.isBlank()) ? nombre : null;
	    categoria = (categoria != null && !categoria.isBlank()) ? categoria : null;
	    pais = (pais != null && !pais.isBlank()) ? pais : null;
	    LocalDate fecha = null;

	    try {
	        if (fechaStr != null && !fechaStr.isBlank()) {
	            fecha = LocalDate.parse(fechaStr); // Asegúrate de que venga con formato "yyyy-MM-dd"
	        }
	    } catch (Exception e) {
	        System.out.println("Fecha inválida: " + fechaStr);
	    }

	    salario = (salario != null && salario > 0) ? salario : null;

	    return vrepo.buscarPorFiltros(nombre, categoria, pais, fecha, salario);
	}


	public List<Vacantes> vacantesPorEmpresa(Empresas empresa) {
	    return vrepo.findByEmpresa(empresa);
	}


	



}
