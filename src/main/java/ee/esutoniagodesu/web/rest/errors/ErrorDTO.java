package ee.esutoniagodesu.web.rest.errors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for transfering error message with a list of field errors.
 */
public class ErrorDTO implements Serializable {

    private static final long serialVersionUID = -249523756561488880L;

    private final String code;
    private final String message;
    private final String description;

    private List<FieldErrorDTO> fieldErrors;

    ErrorDTO(String message) {
        this(message, null);
    }

    ErrorDTO(String message, String description) {
        this(null, message, description);
    }

    ErrorDTO(String code, String message, String description) {
        this(code, message, description, null);
    }

    ErrorDTO(String code, String message, String description, List<FieldErrorDTO> fieldErrors) {
        this.code = code;
        this.message = message;
        this.description = description;
        this.fieldErrors = fieldErrors;
    }

    public void add(String objectName, String field, String message) {
        if (fieldErrors == null) {
            fieldErrors = new ArrayList<>();
        }
        fieldErrors.add(new FieldErrorDTO(objectName, field, message));
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

    public List<FieldErrorDTO> getFieldErrors() {
        return fieldErrors;
    }
}
