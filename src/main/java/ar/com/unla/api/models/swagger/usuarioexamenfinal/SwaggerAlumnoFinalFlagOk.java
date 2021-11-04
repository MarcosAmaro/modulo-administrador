package ar.com.unla.api.models.swagger.usuarioexamenfinal;

import ar.com.unla.api.dtos.response.AlumnoFinalFlagDTO;
import java.util.List;
import lombok.Data;

@Data
public final class SwaggerAlumnoFinalFlagOk {

    private List<AlumnoFinalFlagDTO> data;
}
