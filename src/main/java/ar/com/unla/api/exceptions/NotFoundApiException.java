package ar.com.unla.api.exceptions;

import ar.com.unla.api.constants.CommonsErrorConstants;
import org.springframework.http.HttpStatus;

public class NotFoundApiException extends GeneralApiException {

    public NotFoundApiException() {
        super();
    }

    public NotFoundApiException(String message) {
        super(message);
    }

    @Override
    public String getErrorCode() {
        return CommonsErrorConstants.SEARCH_NOT_FOUND_ERROR_CODE;
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
