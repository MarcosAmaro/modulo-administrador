package ar.com.unla.api.exceptions;

import ar.com.unla.api.constants.CommonsErrorConstants;
import ar.com.unla.api.models.response.ApplicationResponse;
import ar.com.unla.api.models.response.ErrorDetail;
import ar.com.unla.api.models.response.ErrorResponse;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public abstract class RestExceptionHandler extends ResponseEntityExceptionHandler {

    protected static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

    @Value("${spring.application.name}")
    protected String applicationName;

    public RestExceptionHandler() {
    }

    /**
     * Method that is responsible for caching the errors related to the contraints defined in the
     * database model, such as @Column(nuleable = false) or @Column(unique = true).
     */
    @ExceptionHandler({
            DataIntegrityViolationException.class
    })
    protected ResponseEntity<ApplicationResponse<Object>> handleDataBaseExceptions(
            Exception ex) {

        String exceptionName =
                (ex.getCause() != null) ? ex.getCause().getCause()
                        .getClass().getName() : StringUtils.EMPTY;

        ApplicationResponse<Object> applicationResponse;
        HttpStatus status;
        List<String> messages;

        if (SQLIntegrityConstraintViolationException.class.getName().equals(exceptionName)) {
            status = HttpStatus.BAD_REQUEST;

            org.hibernate.exception.ConstraintViolationException constraintViolationException =
                    (org.hibernate.exception.ConstraintViolationException)
                            ex.getCause();

            if (constraintViolationException.getCause().getMessage().contains("Duplicate") &&
                    constraintViolationException.getCause().getMessage().contains("@")) {

                messages = Collections.singletonList(
                        "Email no disponible. Ya existe un usuario con el email indicado.");

            } else {

                messages =
                        Collections.singletonList(
                                "Error SQL: " + constraintViolationException.getCause()
                                        .getMessage());
            }

            applicationResponse =
                    this.getApplicationResponse(CommonsErrorConstants.REQUEST_VALIDATION_ERROR_CODE,
                            messages);

        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            applicationResponse =
                    this.getApplicationResponse(CommonsErrorConstants.DEFAULT_SERVICE_ERROR_CODE,
                            Collections.singletonList(
                                    CommonsErrorConstants.DEFAULT_SERVICE_ERROR_MESSAGE));
        }

        return ResponseEntity.status(status).body(applicationResponse);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    protected ResponseEntity<ApplicationResponse<Object>> handleConstraintViolationException(
            ConstraintViolationException ex) {
        List<String> messages =
                ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
                        .collect(Collectors.toList());
        log.info(CommonsErrorConstants.LOG_VALIDATION_MESSAGE, ex.getClass(), messages);
        ApplicationResponse<Object> applicationResponse =
                this.getApplicationResponse(CommonsErrorConstants.REQUEST_VALIDATION_ERROR_CODE,
                        messages);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(applicationResponse);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    protected ResponseEntity<ApplicationResponse<Object>> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex) {
        String message = String.format(CommonsErrorConstants.TYPE_MISMATCH_PARAM_ERROR_MESSAGE,
                ex.getName());
        log.info(message);
        ApplicationResponse<Object> applicationResponse =
                this.getApplicationResponse(CommonsErrorConstants.TYPE_MISMATCH_PARAM_ERROR_CODE,
                        Collections.singletonList(message));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(applicationResponse);
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<ApplicationResponse<Object>> handleException(Exception ex) {
        log.error(CommonsErrorConstants.LOG_ERROR_MESSAGE, ex.getMessage(), ex);
        ApplicationResponse<Object> applicationResponse =
                this.getApplicationResponse(CommonsErrorConstants.DEFAULT_SERVICE_ERROR_CODE,
                        Collections.singletonList(
                                CommonsErrorConstants.DEFAULT_SERVICE_ERROR_MESSAGE));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(applicationResponse);
    }

    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        ApplicationResponse<Object> applicationResponse =
                this.getApplicationResponse(CommonsErrorConstants.METHOD_NOT_ALLOWED_ERROR_CODE,
                        Collections.singletonList(
                                String.format(
                                        CommonsErrorConstants.METHOD_NOT_ALLOWED_ERROR_MESSAGES,
                                        Arrays.toString(ex.getSupportedMethods()))));
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(applicationResponse);
    }

    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        String message = String.format(CommonsErrorConstants.REQUIRED_PARAM_ERROR_MESSAGE,
                ex.getParameterName());
        log.info(message);
        ApplicationResponse<Object> applicationResponse =
                this.getApplicationResponse(CommonsErrorConstants.REQUEST_VALIDATION_ERROR_CODE,
                        Collections.singletonList(message));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(applicationResponse);
    }

    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message =
                String.format(CommonsErrorConstants.TYPE_MISMATCH_PARAM_ERROR_MESSAGE,
                        ex.getPropertyName());
        log.info(message);
        ApplicationResponse<Object> applicationResponse =
                this.getApplicationResponse(CommonsErrorConstants.TYPE_MISMATCH_PARAM_ERROR_CODE,
                        Collections.singletonList(message));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(applicationResponse);
    }

    protected ResponseEntity<Object> handleServletRequestBindingException(
            ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        return this.getGenericBadRequestApplicationResponse();
    }

    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException exception, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        String message;
        String errorCode;

        String dateTimeExceptionName = Optional.ofNullable(exception)
                .map(Throwable::getCause)
                .map(Throwable::getCause)
                .map(Throwable::getClass)
                .map(Class::getName).orElse(Strings.EMPTY);

        if ("java.time.format.DateTimeParseException".equals(dateTimeExceptionName)) {
            message = CommonsErrorConstants.DATE_FORMAT_INCORRECT_MESSAGE;
            errorCode = CommonsErrorConstants.REQUEST_VALIDATION_ERROR_CODE;
        } else {
            message = Optional.ofNullable(exception.getCause()).map(Throwable::getCause)
                    .map(Throwable::getMessage)
                    .orElse(CommonsErrorConstants.REQUEST_GENERIC_ERROR_MESSAGE);

            errorCode = CommonsErrorConstants.REQUEST_GENERIC_ERROR_CODE;
        }
        ApplicationResponse<Object> applicationResponse =
                this.getApplicationResponse(errorCode, Collections.singletonList(message));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(applicationResponse);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        List<String> errorDetails = ex.getBindingResult().getAllErrors().stream()
                .map(this::generateArgumentNotValidErrorMessage).collect(Collectors.toList());
        ApplicationResponse<Object> applicationResponse =
                this.getApplicationResponse(CommonsErrorConstants.REQUEST_VALIDATION_ERROR_CODE,
                        errorDetails);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(applicationResponse);
    }

    protected ResponseEntity<Object> handleMissingServletRequestPart(
            MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        return this.getGenericBadRequestApplicationResponse();
    }

    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        return this.getGenericBadRequestApplicationResponse();
    }

    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        ApplicationResponse<Object> applicationResponse =
                this.getApplicationResponse(CommonsErrorConstants.UNSUPPORTED_MEDIA_TYPE_ERROR_CODE,
                        Collections.singletonList(
                                CommonsErrorConstants.UNSUPPORTED_MEDIA_TYPE_ERROR_MESSAGE));
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(applicationResponse);
    }

    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
            HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        ApplicationResponse<Object> applicationResponse =
                this.getApplicationResponse(CommonsErrorConstants.UNSUPPORTED_MEDIA_TYPE_ERROR_CODE,
                        Collections.singletonList(
                                CommonsErrorConstants.UNSUPPORTED_MEDIA_TYPE_ERROR_MESSAGE));
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(applicationResponse);
    }

    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        return this.getGenericBadRequestApplicationResponse();
    }

    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(
            AsyncRequestTimeoutException ex, HttpHeaders headers, HttpStatus status,
            WebRequest webRequest) {
        log.error(CommonsErrorConstants.LOG_ERROR_MESSAGE, ex.getMessage(), ex);
        ApplicationResponse<Object> applicationResponse =
                this.getApplicationResponse(CommonsErrorConstants.INTERNAL_ERROR_CODE,
                        Collections.singletonList(CommonsErrorConstants.INTERNAL_ERROR_MESSAGE));
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(applicationResponse);
    }

    protected ApplicationResponse<Object> getApplicationResponse(String code,
            List<String> messages) {

        ErrorDetail errorDetail = new ErrorDetail(code, messages);
        List<ErrorDetail> errors = new ArrayList<>();
        errors.add(errorDetail);
        ErrorResponse errorResponse = new ErrorResponse(this.applicationName, errors);

        return new ApplicationResponse(null, errorResponse);
    }

    protected ResponseEntity<Object> getGenericBadRequestApplicationResponse() {
        ApplicationResponse<Object> applicationResponse =
                this.getApplicationResponse(CommonsErrorConstants.REQUEST_GENERIC_ERROR_CODE,
                        Collections.singletonList(
                                CommonsErrorConstants.REQUEST_GENERIC_ERROR_MESSAGE));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(applicationResponse);
    }

    protected String generateArgumentNotValidErrorMessage(ObjectError e) {
        String message = CommonsErrorConstants.REQUEST_GENERIC_ERROR_MESSAGE;
        if (e.getArguments() != null && e.getArguments().length > 0) {
            String field =
                    ((DefaultMessageSourceResolvable) e.getArguments()[0]).getDefaultMessage();
            String defaultMessage = e.getDefaultMessage();
            if (defaultMessage != null) {
                message = String.format(defaultMessage, field);
            }
        }

        return message;
    }
}
