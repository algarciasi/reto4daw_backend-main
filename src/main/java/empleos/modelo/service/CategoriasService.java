package empleos.modelo.service;

import java.util.List;

import empleos.modelo.dto.CategoriasDto;
import empleos.modelo.entity.Categorias;

public interface CategoriasService {
	
	// apartado B Rol Administrador
	
	List<Categorias> listarCategorias();
	Categorias nuevaCategoria(Categorias categoria);
	Categorias actualizarCategoria(int idCategoria, Categorias datos);
	void eliminarCategoria(int idCategoria);
	Categorias buscarPorId(int idCategoria);
    CategoriasDto actualizar(CategoriasDto dto);




}
