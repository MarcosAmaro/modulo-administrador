package ar.com.unla.api.dtos.response;

import ar.com.unla.api.models.database.DiaSemana;
import ar.com.unla.api.models.database.PeriodoInscripcion;
import ar.com.unla.api.models.database.Turno;
import ar.com.unla.api.models.database.Usuario;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MateriasInscriptasDTO {

    private Long id;

    @ApiModelProperty(notes = "nombre", example = "Sistemas distribuidos",
            position = 1)
    private String nombre;

    @ApiModelProperty(notes = "profesor", example = "Raul Gonzales",
            position = 2)
    private Usuario profesor;

    @ApiModelProperty(notes = "cuatrimestre", example = "1", position = 3)
    private Integer cuatrimestre;

    @ApiModelProperty(notes = "anio", example = "2020", position = 4)
    private Integer anioCarrera;

    @ApiModelProperty(notes = "turno", position = 5)
    private Turno turno;

    @ApiModelProperty(notes = "periodoInscripcion", position = 6)
    private PeriodoInscripcion periodoInscripcion;

    @ApiModelProperty(notes = "dias", position = 7)
    private Set<DiaSemana> dias = new HashSet<>();

    @ApiModelProperty(notes = "inscripto", position = 8)
    private Boolean inscripto;

    @ApiModelProperty(notes = "idInscripcion", position = 9)
    private Long idInscripcion;
}
