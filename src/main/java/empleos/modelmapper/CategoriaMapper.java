package empleos.modelmapper;

import empleos.modelo.dto.CategoriasDto;
import empleos.modelo.entity.Categorias;

public class CategoriaMapper {
	
	public static CategoriasDto toDTO(Categorias categoria) {
		CategoriasDto cdto = new CategoriasDto();
        cdto.setId(categoria.getIdCategoria());
        cdto.setNombre(categoria.getNombre());
        return cdto;
    }

    public static Categorias toEntity(CategoriasDto cdto) {
        Categorias categoria = new Categorias();
        categoria.setIdCategoria(cdto.getId());
        categoria.setNombre(cdto.getNombre());
        return categoria;
    }

}
