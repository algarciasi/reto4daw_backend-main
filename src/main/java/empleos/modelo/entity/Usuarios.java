package empleos.modelo.entity;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@EqualsAndHashCode(of="email")
@Builder
@Entity
@Table(name="usuarios")

@Schema(description = "Entidad que representa un usuario")

public class Usuarios implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "ID unico del usuario", example = "pacosju@cna.com")
	private String email;
	@Schema(description = "Nombre del usuario", example = "Pepe")
	private String nombre;
	@Schema(description = "Apellidos del usuario", example = "Acosta Juan")
	private String apellidos;
	@Schema(description = "Password del usuario", example = "xyzwt123")
	private String password;
	@Schema(description = "Estado del usuario", example = "1")
	private int enabled;
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_Registro")
	@Schema(description = "Fecha de registro del usuario", example = "2024-03-19")
	private Date fecha;
	@Schema(description = "Rol del usuario", example = "CLIENTE")
	private String rol;
	
}
