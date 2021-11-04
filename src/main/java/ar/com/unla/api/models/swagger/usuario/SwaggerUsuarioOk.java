package ar.com.unla.api.models.swagger.usuario;

import ar.com.unla.api.models.database.Usuario;
import lombok.Data;

/**
 * Provides an OK structure response for Swagger (Avoiding to show the errors attribute)
 */
@Data
public final class SwaggerUsuarioOk {

    private Usuario data;

}
