package empleos.modelo.entity;

import java.io.Serializable;
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
@EqualsAndHashCode(of="idSolicitud")
@Builder
@Entity
@Table(name="solicitudes")

@Schema(description = "Entidad que representa una vacante de empleo")

public class Solicitudes implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_solicitud")
	@Schema(description = "ID unico de la solicitud de empleo", example = "1")
	private int idSolicitud;
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha")
	@Schema(description = "Fecha de solicitud de empleo", example = "2024-03-19")
	private Date fecha;
	@Schema(description = "Archivo de solicitud", example = "XXXXXXX")
	@Column(nullable = false, length = 2000)
	private String archivo;
	@Schema(description = "Comentarios", example = "Registrado el usuario X en la vacante Y")
	private String comentarios;
	@Column(name = "estado")
    @Schema(description = "Indica el estado de la vacante", example = "1")
	private int estado;
	@Schema(description = "Curriculum", example = "Licenciado en Ingenieria de Software")
	private String curriculum;
    @ManyToOne
	@JoinColumn(name="id_vacante")
	private Vacantes vacante;
    @ManyToOne
	@JoinColumn(name="email")
	private Usuarios usuario;
}
