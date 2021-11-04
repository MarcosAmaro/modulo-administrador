package ar.com.unla.api.models.swagger.materia;

import ar.com.unla.api.models.database.Materia;
import lombok.Data;

/**
 * Provides an OK structure response for Swagger (Avoiding to show the errors attribute)
 */
@Data
public final class SwaggerMateriaOk {

    private Materia data;

}
