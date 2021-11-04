package ar.com.unla.api.dtos.response;

import ar.com.unla.api.models.database.Materia;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlumnosMateriaDTO {

    @ApiModelProperty(notes = "materia", position = 1)
    private Materia materia;

    @ApiModelProperty(notes = "alumnos", position = 2)
    private List<AlumnoMateriaDTO> alumnos;

}
