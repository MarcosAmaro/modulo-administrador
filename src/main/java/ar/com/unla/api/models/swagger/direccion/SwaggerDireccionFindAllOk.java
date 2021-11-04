package ar.com.unla.api.models.swagger.direccion;

import ar.com.unla.api.models.database.Direccion;
import java.util.List;
import lombok.Data;

/**
 * Provides an OK structure response for Swagger (Avoiding to show the errors attribute)
 */
@Data
public final class SwaggerDireccionFindAllOk {

    private List<Direccion> data;

}
