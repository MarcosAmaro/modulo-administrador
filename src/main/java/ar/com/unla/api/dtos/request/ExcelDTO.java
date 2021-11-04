package ar.com.unla.api.dtos.request;

import ar.com.unla.api.constants.CommonsErrorConstants;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExcelDTO {

    @NotNull(message = CommonsErrorConstants.REQUIRED_PARAM_ERROR_MESSAGE)
    private List<List<String>> excel;
}
