package ar.com.unla.api.dtos.response;

import ar.com.unla.api.models.database.Usuario;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlumnoMateriaDTO {

    @ApiModelProperty(notes = "usuario", position = 1)
    private Usuario usuario;

    @ApiModelProperty(notes = "calificacionExamen", position = 2)
    private float calificacionExamen;

    @ApiModelProperty(notes = "calificacionTps", position = 3)
    private float calificacionTps;

    @ApiModelProperty(notes = "idInscripcion", position = 4)
    private Long idInscripcion;

}
