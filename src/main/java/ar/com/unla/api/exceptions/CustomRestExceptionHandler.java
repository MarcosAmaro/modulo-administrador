package ar.com.unla.api.exceptions;

import ar.com.unla.api.models.response.ApplicationResponse;
import java.util.Collections;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler extends RestExceptionHandler {

    @ExceptionHandler({
            NotFoundApiException.class,
            TransactionBlockedException.class,
            ExcelEmptyException.class
    })
    protected ResponseEntity<ApplicationResponse<Object>> handleValidationOkResponseExceptions(
            GeneralApiException generalApiException) {

        List<String> messages = Collections.singletonList(generalApiException.getMessage());
        ApplicationResponse<Object> applicationResponse = this
                .getApplicationResponse(generalApiException.getErrorCode(), messages);
        return ResponseEntity.status(generalApiException.getStatus())
                .body(applicationResponse);
    }

}
