package ar.com.unla.api.exceptions;

import org.springframework.http.HttpStatus;

public abstract class GeneralApiException extends RuntimeException {

    public GeneralApiException(String message) {
        super(message);
    }

    public GeneralApiException() {
    }

    public abstract String getErrorCode();

    public abstract HttpStatus getStatus();


}
