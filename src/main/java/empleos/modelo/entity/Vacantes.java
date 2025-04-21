package empleos.modelo.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of="idVacante")
@Builder
@Entity
@Table(name="vacantes")

@Schema(description = "Entidad que representa una vacante de empleo")

public class Vacantes implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_vacante")
	@Schema(description = "ID unico de la vacante de empleo", example = "1")
	private int idVacante;
	@Schema(description = "Nombre de la vacante de empleo", example = "Desarrollador Web")
	private String nombre;
	@Schema(description = "Descripcion de la vacante de empleo", example = "Desarrollador Web Java")
	private String descripcion;
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha")
	@Schema(description = "Fecha de publicación de la vacante", example = "2024-03-19")
	private Date fecha;
	@Schema(description = "Salario ofrecido para la vacante", example = "2500.50")
    private Double salario;
    @Enumerated(EnumType.STRING)
    @Schema(description = "Indica el estado de la vacante", example = "1")
	private EstadoVacante estatus;
    @Enumerated(EnumType.ORDINAL)
    @Schema(description = "Indica si la vacante es destacada (1) o no (0)", example = "1")
    private TipoDestacado destacado;
    @Schema(description = "Imagen de la vacante de empleo", example = "www.img.com/dweb")
	private String imagen;
    @Schema(description = "Detalle de la vacante de empleo", example = "Jornada Completa, L-V, 8-17")
	private String detalles;
    @ManyToOne
	@JoinColumn(name="id_categoria")
	private Categorias categoria;
    @ManyToOne
	@JoinColumn(name="id_empresa")
	private Empresas empresa;
}
