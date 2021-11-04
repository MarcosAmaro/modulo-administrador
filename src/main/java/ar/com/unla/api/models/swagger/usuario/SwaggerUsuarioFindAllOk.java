package ar.com.unla.api.models.swagger.usuario;

import ar.com.unla.api.models.database.Usuario;
import java.util.List;
import lombok.Data;

/**
 * Provides an OK structure response for Swagger (Avoiding to show the errors attribute)
 */
@Data
public final class SwaggerUsuarioFindAllOk {

    private List<Usuario> data;

}
