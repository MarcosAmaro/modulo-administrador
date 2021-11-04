package ar.com.unla.api.models.swagger.periodoinspeccion;

import ar.com.unla.api.models.database.PeriodoInscripcion;
import java.util.List;
import lombok.Data;

/**
 * Provides an OK structure response for Swagger (Avoiding to show the errors attribute)
 */
@Data
public final class SwaggerPeriodoInscripcionFindAllOk {

    private List<PeriodoInscripcion> data;

}
