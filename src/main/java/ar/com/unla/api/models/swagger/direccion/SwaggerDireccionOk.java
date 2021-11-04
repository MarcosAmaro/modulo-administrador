package ar.com.unla.api.models.swagger.direccion;

import ar.com.unla.api.models.database.Direccion;
import lombok.Data;

/**
 * Provides an OK structure response for Swagger (Avoiding to show the errors attribute)
 */
@Data
public final class SwaggerDireccionOk {

    private Direccion data;

}
