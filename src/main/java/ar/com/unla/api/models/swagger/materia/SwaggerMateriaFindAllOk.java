package ar.com.unla.api.models.swagger.materia;

import ar.com.unla.api.models.database.Materia;
import java.util.List;
import lombok.Data;

/**
 * Provides an OK structure response for Swagger (Avoiding to show the errors attribute)
 */
@Data
public final class SwaggerMateriaFindAllOk {

    private List<Materia> data;

}
