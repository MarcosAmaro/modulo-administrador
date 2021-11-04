package ar.com.unla.api.models.swagger.usuariomateria;

import ar.com.unla.api.dtos.response.MateriasInscriptasDTO;
import java.util.List;
import lombok.Data;

/**
 * Provides an OK structure response for Swagger (Avoiding to show the errors attribute)
 */
@Data
public final class SwaggerUsuarioMateriasInscriptasOK {

    private List<MateriasInscriptasDTO> data;
}
