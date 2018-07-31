package project.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseDTO {

    private String message;
    private final boolean success;

    public ResponseDTO(ResponseDTO parent) {
        this.message = parent.getMessage();
        this.success = parent.isSuccess();
    }

    public ResponseDTO(final String message, final boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public static ResponseEntity<ResponseDTO> createSuccessResponse() {
        return new ResponseEntity<>(new ResponseDTO("Success", true), HttpStatus.OK);
    }

    public static ResponseEntity<ResponseDTO> createSuccessResponse(String message) {
        return new ResponseEntity<>(new ResponseDTO(message, true), HttpStatus.OK);
    }

    public static ResponseEntity<ResponseDTO> createSuccessResponse(String message, HttpStatus status) {
        return new ResponseEntity<>(new ResponseDTO(message, true), status);
    }

    public static ResponseEntity<ResponseDTO> createFailureResponse() {
        return new ResponseEntity<>(new ResponseDTO("Fail", false), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<ResponseDTO> createFailureResponse(String message) {
        return new ResponseEntity<>(new ResponseDTO(message, false), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<ResponseDTO> createFailureResponse(HttpStatus status) {
        return new ResponseEntity<>(new ResponseDTO("Fail", false), status);
    }

    public static ResponseEntity<ResponseDTO> createFailureResponse(String message, HttpStatus status) {
        return new ResponseEntity<>(new ResponseDTO(message, false), status);
    }

    public static ResponseEntity<ResponseDTO> createFailureResponse(String message, Exception exception) {
        return new ResponseEntity<>(
                new ResponseExceptionDetailDTO(message, exception.toString()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<ResponseDTO> createFailureResponse(String message, HttpStatus status,
                                                                    Exception exception) {
        return new ResponseEntity<>( new ResponseExceptionDetailDTO(message, exception.toString()), status);
    }

    public static ResponseEntity<ResponseDTO> userNotExist() {
        return createFailureResponse("User not exist", HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<ResponseDTO> categoryNotExist() {
        return createFailureResponse("Category not exist!", HttpStatus.BAD_REQUEST);
    }

    /**
     * Used in case when response is the cause of some exception
     */
    private static class ResponseExceptionDetailDTO extends ResponseDTO {
        private String exception;

        public ResponseExceptionDetailDTO(ResponseDTO parent) {
            super(parent);
        }


        public ResponseExceptionDetailDTO(String message, String exception) {
            super(message, false);
            this.exception = exception;
        }

        public String getException() {
            return exception;
        }

        public ResponseExceptionDetailDTO setException(String exception) {
            this.exception = exception;
            return this;
        }
    }

}
