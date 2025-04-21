package empleos.modelo.dto;

import lombok.Data;

/*@Data
public class SolicitudRespuestaDto {
    private int idSolicitud;
    private String fecha;
    //private String archivo;
    private String comentarios;
    private int estado;
    private String curriculum;
    private String nombreVacante;
    private String emailUsuario;
}*/

@Data
public class SolicitudRespuestaDto {
    private int idSolicitud;
    private String fecha; // Formato ISO o personalizado
    private String comentarios;
    private int estado;
    private String curriculum;

    private String nombreVacante;
    private String nombreEmpresa;
    private String nombreCategoria;

    private String emailUsuario;
}
