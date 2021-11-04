package ar.com.unla.api.dtos.response;

import ar.com.unla.api.models.database.Usuario;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlumnoFinalDTO {

    @ApiModelProperty(notes = "usuario", position = 1)
    private Usuario usuario;

    @ApiModelProperty(notes = "calificacion", position = 2)
    private float calificacion;

    @ApiModelProperty(notes = "idInscripcion", position = 3)
    private Long idInscripcion;

}
