package ar.com.unla.api.models.swagger.usuarioexamenfinal;

import ar.com.unla.api.dtos.response.AlumnoMateriaFlagDTO;
import java.util.List;
import lombok.Data;

@Data
public final class SwaggerAlumnoMateriaFlagOk {

    private List<AlumnoMateriaFlagDTO> data;
}
