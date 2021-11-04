package ar.com.unla.api.dtos.request;

import ar.com.unla.api.annotations.ValueOfEnum;
import ar.com.unla.api.constants.CommonsErrorConstants;
import ar.com.unla.api.models.enums.DiasSemanaEnum;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DiaSemanaDTO {

    @ValueOfEnum(enumClass = DiasSemanaEnum.class)
    @NotBlank(message = CommonsErrorConstants.REQUIRED_PARAM_ERROR_MESSAGE)
    @ApiModelProperty(required = true, position = 1)
    private String nombre;
}
