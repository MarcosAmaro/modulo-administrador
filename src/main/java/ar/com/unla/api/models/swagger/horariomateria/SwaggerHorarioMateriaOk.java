package ar.com.unla.api.models.swagger.horariomateria;

import ar.com.unla.api.models.database.DiaSemana;
import lombok.Data;

/**
 * Provides an OK structure response for Swagger (Avoiding to show the errors attribute)
 */
@Data
public final class SwaggerHorarioMateriaOk {

    private DiaSemana data;

}
