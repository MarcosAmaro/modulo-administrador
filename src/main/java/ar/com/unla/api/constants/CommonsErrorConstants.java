package ar.com.unla.api.constants;

public final class CommonsErrorConstants {

    public static final String DEFAULT_SERVICE_ERROR_CODE = "UNLA-ERR-100";

    public static final String DEFAULT_SERVICE_ERROR_MESSAGE = "Error al ejecutar el servicio";

    public static final String INTERNAL_ERROR_CODE = "UNLA-ERR-001";

    public static final String INTERNAL_ERROR_MESSAGE = "Error interno del sistema";

    public static final String METHOD_NOT_ALLOWED_ERROR_CODE = "UNLA-ERR-002";

    public static final String METHOD_NOT_ALLOWED_ERROR_MESSAGES =
            "Los métodos HTTP permitidos son: %s";

    public static final String UNSUPPORTED_MEDIA_TYPE_ERROR_CODE = "UNLA-ERR-003";

    public static final String UNSUPPORTED_MEDIA_TYPE_ERROR_MESSAGE = "Content type no soportado";

    public static final String REQUEST_GENERIC_ERROR_CODE = "UNLA-ERR-004";

    public static final String REQUEST_GENERIC_ERROR_MESSAGE = "La request es incorrecta";

    public static final String DATE_FORMAT_INCORRECT_MESSAGE = "Fecha ingresada incorrecta. El "
            + "formato correcto es: yyyy-MM-dd";

    public static final String TYPE_MISMATCH_PARAM_ERROR_CODE = "UNLA-ERR-005";

    public static final String TYPE_MISMATCH_PARAM_ERROR_MESSAGE =
            "El parámetro %s no es del tipo esperado";

    public static final String REQUIRED_PARAM_ERROR_CODE = "UNLA-ERR-006";

    public static final String REQUIRED_PARAM_ERROR_MESSAGE =
            "El parámetro %s es obligatorio y no debe estar vacío";

    public static final String REQUEST_VALIDATION_ERROR_CODE = "UNLA-ERR-007";

    public static final String STRING_NUMERIC_ERROR_MESSAGE = "El parámetro %s debe ser numérico";

    public static final String INCORRECT_MAIL_MESSAGE =
            "El mail ingresado no tiene el formato correcto";

    public static final String INCORRECT_SIZE_IMAGE_MESSAGE =
            "La imagen ingresado supera el tamaño esperado";

    public static final String SIZE_DOCUMENT_NUMBER_ERROR =
            "El parámetro dni no debe tener más de {max} caracteres.";

    public static final String MAX_VALUE_ERROR = "El parametro %s no puede ser mayor a {value}";

    public static final String MIN_VALUE_ERROR = "El parametro %s no puede ser menor a {value}";

    public static final String QUALIFICATION_VALUE_ERROR = "El parámetro %s "
            + "puede tener {integer} cifras enteras y {fraction} cifras decimales como máximo.";

    public static final String FUTURE_DATE_ERROR_MESSAGE =
            "El parametro %s debe tener una fecha posterior a la actual";

    public static final String SEARCH_NOT_FOUND_ERROR_CODE = "UNLA-ERR-008";

    public static final String LOCKED_TRANSACTION_ERROR_CODE = "UNLA-ERR-09";

    public static final String EXCEL_EMPTY_ERROR_CODE = "UNLA-ERR-10";

    public static final String ROLE_NOT_FOUND_ERROR_MESSAGE =
            "El rol del usuario indicado es incorrecto";

    public static final String LIST_INTERNAL_ERROR_MESSAGE =
            "Error interno al intentar traer una lista de %s";

    public static final String LOG_ERROR_MESSAGE = "Messages: {}. Stacktrace: ";

    public static final String LOG_VALIDATION_MESSAGE = "Exception: {}. Messages: {}";

    private CommonsErrorConstants() {
    }
}
