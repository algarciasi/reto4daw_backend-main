package empleos.modelo.entity;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of="idEmpresa")
@Builder
@Entity
@Table(name="empresas")

@Schema(description = "Entidad que representa una empresa")

public class Empresas implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_empresa")
	@Schema(description = "ID unico de la empresa", example = "1")
	private int idEmpresa;
	@Schema(description = "CIF de la empresa", example = "A123456789")
	private String cif;
	@Schema(description = "Nombre de la empresa", example = "Consultoria Informática CNA")
	@Column(name="nombre_empresa")
	private String nombre;
	@Column(name="direccion_fiscal")
	@Schema(description = "Direccion de la empresa", example = "Avenida Madrid 33")
	private String direccion;
	@Schema(description = "Pais de la empresa", example = "España")
	private String pais;
	@ManyToOne
	@JoinColumn(name="email", referencedColumnName = "email")
	@Schema(description = "Email de la empresa", example = "cicna@cna.es")
	private Usuarios usuario;
	
}
