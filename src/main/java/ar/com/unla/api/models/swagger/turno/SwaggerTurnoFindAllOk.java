package ar.com.unla.api.models.swagger.turno;

import ar.com.unla.api.models.database.Turno;
import java.util.List;
import lombok.Data;

/**
 * Provides an OK structure response for Swagger (Avoiding to show the errors attribute)
 */
@Data
public final class SwaggerTurnoFindAllOk {

    private List<Turno> data;

}
