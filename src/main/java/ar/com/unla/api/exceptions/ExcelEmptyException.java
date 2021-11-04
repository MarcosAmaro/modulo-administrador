package ar.com.unla.api.exceptions;

import ar.com.unla.api.constants.CommonsErrorConstants;
import org.springframework.http.HttpStatus;

public class ExcelEmptyException extends GeneralApiException {

    public ExcelEmptyException() {
        super();
    }

    public ExcelEmptyException(String message) {
        super(message);
    }

    @Override
    public String getErrorCode() {
        return CommonsErrorConstants.EXCEL_EMPTY_ERROR_CODE;
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
