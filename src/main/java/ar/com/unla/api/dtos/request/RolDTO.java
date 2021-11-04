package ar.com.unla.api.dtos.request;

import ar.com.unla.api.annotations.ValueOfEnum;
import ar.com.unla.api.constants.CommonsErrorConstants;
import ar.com.unla.api.models.enums.RolesEnum;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RolDTO {

    @ValueOfEnum(enumClass = RolesEnum.class)
    @NotBlank(message = CommonsErrorConstants.REQUIRED_PARAM_ERROR_MESSAGE)
    private String descripcion;
}
