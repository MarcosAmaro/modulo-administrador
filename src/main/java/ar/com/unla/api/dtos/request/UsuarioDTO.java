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
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@NoArgsConstructor
public class UsuarioDTO {

    @NotBlank(message = CommonsErrorConstants.REQUIRED_PARAM_ERROR_MESSAGE)
    @ApiModelProperty(required = true, position = 1)
    private String nombre;

    @NotBlank(message = CommonsErrorConstants.REQUIRED_PARAM_ERROR_MESSAGE)
    @ApiModelProperty(required = true, position = 2)
    private String apellido;

    @NotBlank(message = CommonsErrorConstants.REQUIRED_PARAM_ERROR_MESSAGE)
    @Pattern(regexp = "[0-9]+", message = CommonsErrorConstants.STRING_NUMERIC_ERROR_MESSAGE)
    @Size(max = 8, message = CommonsErrorConstants.SIZE_DOCUMENT_NUMBER_ERROR)
    @ApiModelProperty(required = true, position = 3)
    private String dni;

    @NotBlank(message = CommonsErrorConstants.REQUIRED_PARAM_ERROR_MESSAGE)
    @Pattern(regexp = "[0-9]+", message = CommonsErrorConstants.STRING_NUMERIC_ERROR_MESSAGE)
    @ApiModelProperty(required = true, position = 4)
    private String telefono;

    @NotBlank(message = CommonsErrorConstants.REQUIRED_PARAM_ERROR_MESSAGE)
    @Email(message = CommonsErrorConstants.INCORRECT_MAIL_MESSAGE)
    @ApiModelProperty(required = true, position = 5)
    private String email;

    @NotBlank(message = CommonsErrorConstants.REQUIRED_PARAM_ERROR_MESSAGE)
    @ApiModelProperty(required = true, position = 6)
    private String password;

    @Valid
    @NotNull(message = CommonsErrorConstants.REQUIRED_PARAM_ERROR_MESSAGE)
    @ApiModelProperty(required = true, position = 7)
    private DireccionDTO direccion;

    @Size(max = 2000000000, message = CommonsErrorConstants.INCORRECT_SIZE_IMAGE_MESSAGE)
    @ApiModelProperty(position = 8)
    private String imagen;

    @NotNull(message = CommonsErrorConstants.REQUIRED_PARAM_ERROR_MESSAGE)
    @ApiModelProperty(required = true, position = 9)
    private Boolean primerIngreso;

    @NotNull(message = CommonsErrorConstants.REQUIRED_PARAM_ERROR_MESSAGE)
    @ApiModelProperty(required = true, position = 10)
    private Long idRol;

    /*
    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }*/
}
