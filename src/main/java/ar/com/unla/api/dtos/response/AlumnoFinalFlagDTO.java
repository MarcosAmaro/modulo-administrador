package ar.com.unla.api.dtos.response;

import ar.com.unla.api.models.database.Usuario;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlumnoFinalFlagDTO {

    @ApiModelProperty(notes = "usuario", position = 1)
    private Usuario usuario;

    @ApiModelProperty(notes = "inscripto", position = 2)
    private boolean inscripto;

    @ApiModelProperty(notes = "calificacion", position = 3)
    private float calificacion;

    @ApiModelProperty(notes = "idInscripcion", position = 4)
    private Long idInscripcion;

    @ApiModelProperty(notes = "idExamenFinal", position = 4)
    private Long idExamenFinal;
}
