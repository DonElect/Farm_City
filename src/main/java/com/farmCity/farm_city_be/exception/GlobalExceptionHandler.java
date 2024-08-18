package com.farmCity.farm_city_be.exception;


import com.farmCity.farm_city_be.models.ApiResponse;
import com.farmCity.farm_city_be.models.ErrorDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidOTPException.class)
    public ResponseEntity<ApiResponse<ErrorDetail>> handleInvalidOTPException(final InvalidOTPException exception){
        ErrorDetail errorDetail = ErrorDetail.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .debugMessage(null)
                .build();
        ApiResponse<ErrorDetail> response = new ApiResponse<>("400", exception.getMessage(), errorDetail);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmailNotSentException.class)
    public ResponseEntity<ApiResponse<ErrorDetail>> handleEmailNotSentException(final EmailNotSentException exception){
        ErrorDetail errorDetail = ErrorDetail.builder()
                .message(exception.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .debugMessage("Email sending failed. Try again.")
                .build();
        ApiResponse<ErrorDetail> response = new ApiResponse<>("400", exception.getMessage(), errorDetail);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ChangeDefaultPasswordException.class)
    public ResponseEntity<ApiResponse<ErrorDetail>> handleChangeDefaultPasswordException(final ChangeDefaultPasswordException exception){
        ErrorDetail errorDetail = ErrorDetail.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .debugMessage("Update the default password for the driver")
                .build();
        ApiResponse<ErrorDetail> response = new ApiResponse<>("400", exception.getMessage(), errorDetail);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ApiResponse<ErrorDetail>> handleGenericExceptions(final GenericException exception){
        ErrorDetail errorDetail = ErrorDetail.builder()
                .message(exception.getMessage())
                .status(exception.getStatusCode())
                .debugMessage(exception.getDebugMessage())
                .build();
        ApiResponse<ErrorDetail> response = new ApiResponse<>("401", exception.getMessage(), errorDetail);
        return new ResponseEntity<>(response, exception.getStatusCode());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<ErrorDetail>> handleUnauthorizedException(final UnauthorizedException exception){
        ErrorDetail errorDetail = ErrorDetail.builder()
                .message(exception.getMessage())
                .status(HttpStatus.UNAUTHORIZED)
                .debugMessage("Unauthorized user.")
                .build();
        ApiResponse<ErrorDetail> response = new ApiResponse<>("401", exception.getMessage(), errorDetail);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ApiResponse<ErrorDetail>> handlePasswordMismatchException(final PasswordMismatchException exception){
        ErrorDetail errorDetail = ErrorDetail.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .debugMessage("Check passwords and try again")
                .build();
        ApiResponse<ErrorDetail> response = new ApiResponse<>("400", exception.getMessage(), errorDetail);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ApiResponse<ErrorDetail>> handleDuplicateEmailException(final DuplicateEmailException exception){
        ErrorDetail errorDetail = ErrorDetail.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .debugMessage("Email already registered. Login or register with a new email.")
                .build();
        ApiResponse<ErrorDetail> response = new ApiResponse<>("400", exception.getMessage(), errorDetail);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiResponse<ErrorDetail>> handleInvalidPasswordException(final InvalidPasswordException exception){
        ErrorDetail errorDetail = ErrorDetail.builder()
                .message(exception.getMessage())
                .status(HttpStatus.NOT_ACCEPTABLE)
                .debugMessage("Check password and try again.")
                .build();
        ApiResponse<ErrorDetail> response = new ApiResponse<>("400", exception.getMessage(), errorDetail);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<ErrorDetail>> handleUserNotFoundException(final UserNotFoundException exception){
        ErrorDetail errorDetail = ErrorDetail.builder()
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .debugMessage("Check email and try again.")
                .build();
        ApiResponse<ErrorDetail> response = new ApiResponse<>("400", exception.getMessage(), errorDetail);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenExpirationException.class)
    public ResponseEntity<ApiResponse<ErrorDetail>> handleTokenExpirationException(final TokenExpirationException exception){
        ErrorDetail errorDetail = ErrorDetail.builder()
                .message(exception.getMessage())
                .status(HttpStatus.UNAUTHORIZED)
                .debugMessage("Please ask for a new link.")
                .build();
        ApiResponse<ErrorDetail> response = new ApiResponse<>("401", exception.getMessage(), errorDetail);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(UserNotVerifiedException.class)
    public ResponseEntity<ApiResponse<ErrorDetail>> handleUserNotVerifiedException(final UserNotVerifiedException exception){
        ErrorDetail errorDetail = ErrorDetail.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .debugMessage("Verify email and try again.")
                .build();
        ApiResponse<ErrorDetail> response = new ApiResponse<>("401", exception.getMessage(), errorDetail);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ApiResponse<String>> handleMethodArgumentNotValidExceptionException(MethodArgumentNotValidException exception) {
        String errorMessage = "Request validation failure. Please check your request data.";
        BindingResult result = exception.getBindingResult();
        FieldError fieldError = result.getFieldError();
        if(fieldError != null) {
            errorMessage = fieldError.getDefaultMessage();
        }
        ApiResponse<String> apiResponse = new ApiResponse<>("400", errorMessage, null);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
