package ar.com.unla.api.models.swagger.usuarioexamenfinal;

import ar.com.unla.api.dtos.response.AlumnosFinalDTO;
import java.util.List;
import lombok.Data;

/**
 * Provides an OK structure response for Swagger (Avoiding to show the errors attribute)
 */
@Data
public final class SwaggerAlumnosFinalOk {

    private List<AlumnosFinalDTO> data;
}
