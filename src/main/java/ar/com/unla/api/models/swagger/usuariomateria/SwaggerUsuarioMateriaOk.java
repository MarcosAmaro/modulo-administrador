package ar.com.unla.api.models.swagger.usuariomateria;

import ar.com.unla.api.models.database.UsuarioMateria;
import lombok.Data;

/**
 * Provides an OK structure response for Swagger (Avoiding to show the errors attribute)
 */
@Data
public final class SwaggerUsuarioMateriaOk {

    private UsuarioMateria data;

}
