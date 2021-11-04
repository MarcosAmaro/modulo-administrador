package ar.com.unla.api.dtos.request;

import ar.com.unla.api.constants.CommonsErrorConstants;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DatosContactoUsuarioDTO {

    @NotBlank(message = CommonsErrorConstants.REQUIRED_PARAM_ERROR_MESSAGE)
    @Pattern(regexp = "[0-9]+", message = CommonsErrorConstants.STRING_NUMERIC_ERROR_MESSAGE)
    @ApiModelProperty(required = true, position = 1)
    private String telefono;

    @NotBlank(message = CommonsErrorConstants.REQUIRED_PARAM_ERROR_MESSAGE)
    @Email(message = CommonsErrorConstants.INCORRECT_MAIL_MESSAGE)
    @ApiModelProperty(required = true, position = 1)
    private String email;

    @Valid
    @NotNull(message = CommonsErrorConstants.REQUIRED_PARAM_ERROR_MESSAGE)
    @ApiModelProperty(required = true, position = 3)
    private DireccionDTO direccion;

    @Size(max = 2000000000, message = CommonsErrorConstants.INCORRECT_SIZE_IMAGE_MESSAGE)
    @ApiModelProperty(position = 4)
    private String imagen;
}
