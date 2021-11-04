package ar.com.unla.api.models.swagger.horariomateria;

import ar.com.unla.api.models.database.DiaSemana;
import java.util.List;
import lombok.Data;

/**
 * Provides an OK structure response for Swagger (Avoiding to show the errors attribute)
 */
@Data
public final class SwaggerHorarioMateriaFindAllOk {

    private List<DiaSemana> data;

}
