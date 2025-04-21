package empleos.modelo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import empleos.modelmapper.CategoriaMapper;
import empleos.modelo.dto.CategoriasDto;
import empleos.modelo.entity.Categorias;
import empleos.modelo.repository.CategoriasRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoriasServiceImpl implements CategoriasService{
	
	@Autowired
	private CategoriasRepository crepo;

	@Override
	public List<Categorias> listarCategorias() {
		return crepo.findAll();
	}

	@Override
	public Categorias nuevaCategoria(Categorias categoria) {
		return crepo.save(categoria);
	}

	@Override
	public Categorias actualizarCategoria(int idCategoria, Categorias datos) {
		return crepo.findById(idCategoria)
		        .map(cat -> {
		            cat.setNombre(datos.getNombre());
		            cat.setDescripcion(datos.getDescripcion());
		            return crepo.save(cat);
		        })
		        .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + idCategoria));
	}

	@Override
	public void eliminarCategoria(int idCategoria) {
	    Optional<Categorias> categoriaOpt = crepo.findById(idCategoria);

	    if (categoriaOpt.isEmpty()) {
	        throw new RuntimeException("La categoría no existe");
	    }

	    Categorias categoria = categoriaOpt.get();

	    if (!categoria.getVacantes().isEmpty()) {
	        throw new DataIntegrityViolationException("No se puede eliminar la categoría porque tiene vacantes asociadas.");
	    }

	    crepo.deleteById(idCategoria);
	}


	
	@Override
	public Categorias buscarPorId(int idCategoria) {
	    return crepo.findById(idCategoria)
	        .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + idCategoria));
	}
	
	@Override
    public CategoriasDto actualizar(CategoriasDto dto) {
        Optional<Categorias> optionalCategoria = crepo.findById(dto.getId());

        if (optionalCategoria.isEmpty()) {
            throw new EntityNotFoundException("No se encontró la categoría con ID: " + dto.getId());
        }

        Categorias categoria = optionalCategoria.get();
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());

        Categorias actualizada = crepo.save(categoria);
        return CategoriaMapper.toDTO(actualizada);
    }


}
