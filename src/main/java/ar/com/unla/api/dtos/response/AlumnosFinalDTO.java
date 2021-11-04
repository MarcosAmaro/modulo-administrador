package ar.com.unla.api.dtos.response;

import ar.com.unla.api.models.database.ExamenFinal;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlumnosFinalDTO {

    @ApiModelProperty(notes = "examenFinal", position = 1)
    private ExamenFinal examenFinal;

    @ApiModelProperty(notes = "alumnos", position = 2)
    private List<AlumnoFinalDTO> alumnos;
}
