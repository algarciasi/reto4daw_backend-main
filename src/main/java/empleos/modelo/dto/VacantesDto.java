package empleos.modelo.dto;

import java.util.Date;

import empleos.modelo.entity.Empresas;
import lombok.Data;

@Data
public class VacantesDto {

    private int idVacante;
    private String nombre;
    private String descripcion;
    private Date fecha;
	private String estatus;
	private String destacado;
	private Empresas empresa;
    private double salario;
    private String detalles;
    private String nombreEmpresa;
    private String nombreCategoria;
    private String pais;


    
    private Integer idCategoria;
}

