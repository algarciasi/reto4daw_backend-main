package empleos.modelo.dto;

import empleos.modelo.entity.Empresas;
import empleos.modelo.entity.Usuarios;
import lombok.Data;

@Data
public class EmpresasDto {

	private int idEmpresa;
	private Empresas empresa;
    private Usuarios usuario;
}
