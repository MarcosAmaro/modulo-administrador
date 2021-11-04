package ar.com.unla.api.dtos.request;

import ar.com.unla.api.constants.CommonsErrorConstants;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginUsuarioDTO {

    @NotBlank(message = CommonsErrorConstants.REQUIRED_PARAM_ERROR_MESSAGE)
    @Email(message = CommonsErrorConstants.INCORRECT_MAIL_MESSAGE)
    @ApiModelProperty(required = true, position = 1)
    private String email;

    @NotBlank(message = CommonsErrorConstants.REQUIRED_PARAM_ERROR_MESSAGE)
    @ApiModelProperty(required = true, position = 2)
    private String password;
}
