package ar.com.unla.api.models.swagger.usuariomateria;

import ar.com.unla.api.dtos.response.AlumnosMateriaDTO;
import java.util.List;
import lombok.Data;

/**
 * Provides an OK structure response for Swagger (Avoiding to show the errors attribute)
 */
@Data
public final class SwaggerAlumnosMateriaOK {

    private List<AlumnosMateriaDTO> data;

}
