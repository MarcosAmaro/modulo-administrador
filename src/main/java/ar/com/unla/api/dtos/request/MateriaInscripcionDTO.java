package ar.com.unla.api.dtos.request;

import ar.com.unla.api.constants.CommonsErrorConstants;
import io.swagger.annotations.ApiModelProperty;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class MateriaInscripcionDTO {

    @Valid
    @NotNull(message = CommonsErrorConstants.REQUIRED_PARAM_ERROR_MESSAGE)
    @ApiModelProperty(required = true, position = 6)
    private PeriodoInscripcionDTO periodoInscripcion;

    @ApiModelProperty(position = 7)
    private Set<Long> dias;
}
