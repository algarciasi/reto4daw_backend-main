package empleos.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import empleos.modelmapper.CategoriaMapper;
import empleos.modelo.dto.CategoriasDto;
import empleos.modelo.entity.Categorias;
import empleos.modelo.service.CategoriasService;

@RestController
@RequestMapping("/categorias")
public class CategoriasController {

    @Autowired
    private CategoriasService cserv;

    @GetMapping("/todas")
    public ResponseEntity<List<CategoriasDto>> listarCategorias() {
        List<CategoriasDto> categoriasDTO = cserv.listarCategorias()
            .stream()
            .map(CategoriaMapper::toDTO)
            .toList();
            
        return ResponseEntity.ok(categoriasDTO);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<CategoriasDto> buscarPorId(@PathVariable int id) {
        CategoriasDto cdto = CategoriaMapper.toDTO(cserv.buscarPorId(id));
        return ResponseEntity.ok(cdto);
    }
    

    @PostMapping("/nueva")
    public ResponseEntity<Categorias> crearCategoria(@RequestBody Categorias categoria) {
        Categorias nueva = cserv.nuevaCategoria(categoria);
        return ResponseEntity.ok(nueva);
    }

    @PutMapping("actualizar/{id}")
    public ResponseEntity<CategoriasDto> actualizarCategoria(
            @PathVariable int id,
            @RequestBody CategoriasDto dto) {
        
        dto.setId(id); // aseguramos que el ID del path se usa
        CategoriasDto actualizada = cserv.actualizar(dto);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable int id) {
        try {
            cserv.eliminarCategoria(id);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // <- IMPORTANTE
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
