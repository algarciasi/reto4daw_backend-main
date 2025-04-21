package empleos.modelmapper;

import java.util.function.Function;

import empleos.modelo.dto.VacantesDto;
import empleos.modelo.entity.Vacantes;

public class VacanteMapper implements Function<Vacantes, VacantesDto> {

    @Override
    public VacantesDto apply(Vacantes vacante) {
        VacantesDto dto = new VacantesDto();

        dto.setIdVacante(vacante.getIdVacante());
        dto.setNombre(vacante.getNombre());
        dto.setDescripcion(vacante.getDescripcion());
        dto.setFecha(vacante.getFecha());
        dto.setEstatus(vacante.getEstatus().name());
        dto.setDestacado(vacante.getDestacado().name());
        dto.setSalario(vacante.getSalario());
        dto.setDetalles(vacante.getDetalles());
        dto.setPais(vacante.getEmpresa() != null ? vacante.getEmpresa().getPais() : null);



        if (vacante.getEmpresa() != null) {
            dto.setEmpresa(vacante.getEmpresa());
            dto.setNombreEmpresa(vacante.getEmpresa().getNombre());
        }

        if (vacante.getCategoria() != null) {
            dto.setIdCategoria(vacante.getCategoria().getIdCategoria());
            dto.setNombreCategoria(vacante.getCategoria().getNombre());
        }

        return dto;
    }
}