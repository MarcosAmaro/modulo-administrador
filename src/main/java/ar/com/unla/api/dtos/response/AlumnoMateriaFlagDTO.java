package ar.com.unla.api.dtos.response;

import ar.com.unla.api.models.database.Usuario;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlumnoMateriaFlagDTO {

    @ApiModelProperty(notes = "usuario", position = 1)
    private Usuario usuario;

    @ApiModelProperty(notes = "inscripto", position = 2)
    private boolean inscripto;

    @ApiModelProperty(notes = "calificacionExamen", position = 3)
    private float calificacionExamen;

    @ApiModelProperty(notes = "calificacionTps", position = 4)
    private float calificacionTps;

    @ApiModelProperty(notes = "idInscripcion", position = 5)
    private Long idInscripcion;
}
