package empleos.modelo.entity;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of="idCategoria")
@Builder
@Entity
@Table(name="categorias")

@Schema(description = "Categoria en una empresa")

public class Categorias implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_categoria")
	@Schema(description = "ID unico de la categoria", example = "1")
	private int idCategoria;
	@Schema(description = "Nombre de la categoria", example = "Desarrolladores")
	private String nombre;
	@Schema(description = "Descripcion de la vacante de empleo", example = "Desarrolladores Java")
	private String descripcion;
	@OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
	private List<Vacantes> vacantes;

}
