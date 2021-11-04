package ar.com.unla.api.exceptions;

import ar.com.unla.api.constants.CommonsErrorConstants;
import org.springframework.http.HttpStatus;

public class TransactionBlockedException extends GeneralApiException {

    public TransactionBlockedException() {
        super();
    }

    public TransactionBlockedException(String message) {
        super(message);
    }

    @Override
    public String getErrorCode() {
        return CommonsErrorConstants.LOCKED_TRANSACTION_ERROR_CODE;
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.LOCKED;
    }

}
