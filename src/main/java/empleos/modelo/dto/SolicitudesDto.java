package empleos.modelo.dto;

import empleos.modelo.entity.Solicitudes;
import lombok.Data;

@Data
public class SolicitudesDto {
	
	private int idVacante;
    private String emailUsuario;
    private Solicitudes solicitud;
	
}
