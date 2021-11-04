package ar.com.unla.api.models.swagger.turno;

import ar.com.unla.api.models.database.Turno;
import lombok.Data;

/**
 * Provides an OK structure response for Swagger (Avoiding to show the errors attribute)
 */
@Data
public final class SwaggerTurnoOk {

    private Turno data;

}
