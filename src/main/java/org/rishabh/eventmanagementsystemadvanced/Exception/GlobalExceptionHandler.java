package org.rishabh.eventmanagementsystemadvanced.Exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<ApiResponse> handleEmailException(EmailException ex){
        log.error(" EmailException: {}"  , ex.getMessage());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ImageException.class)
    public ResponseEntity<ApiResponse> handleImageException(ImageException ex){
        log.error(" ImageException: {}"  , ex.getMessage());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse> handleUserNotFoundException(UserNotFoundException ex){
        log.error(" Caught UserNotFoundException "  , ex);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("User not found");
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ApiResponse> handleCategoryNotFoundException(CategoryNotFoundException ex){
        log.error(" Caught UserNotFoundException "  , ex);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Category not found");
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ApiResponse>handleEventNotFoundException(EventNotFoundException ex){
        log.error(" Caught EventNotFoundException "  , ex);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Event Not Found");
        return  new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(QrCodeNotFoundException.class)
    public ResponseEntity<ApiResponse> handleQrCodeNotFoundException(QrCodeNotFoundException ex){
        log.error(" Caught QrCodeNotFoundException "  , ex);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("QrCode Not Found");
        return  new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<ApiResponse> handleTicketNotFoundException(TicketNotFoundException ex){
        log.error(" Caught TicketNotFoundException "  , ex);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Ticket Not Found");
        return  new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TicketTypeException.class)
    public ResponseEntity<ApiResponse> handleTicketTypeException(TicketTypeException ex){
        log.error(" Caught TicketTypeException "  , ex);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Ticket Type Not Found");
        return  new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderItemNotFound.class)
    public ResponseEntity<ApiResponse> handleOrderItemNotFound(OrderItemNotFound ex){
        log.error(" Caught OrderItemNotFound "  , ex);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Order Item Not Found");
        return  new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderNotFound.class)
    public ResponseEntity<ApiResponse> handleOrderNotFound(OrderNotFound ex){
        log.error(" Caught OrderNotFound "  , ex);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Order Not Found");
        return  new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotActivatedException.class)
    public ResponseEntity<ApiResponse> handleUserNotActivatedException(UserNotActivatedException ex){
        log.error(" Caught UserNotActivatedException "  , ex);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("User Not Activated");
        return  new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> hanldemehtodargumentnotvlaidexception(MethodArgumentNotValidException ex){
        log.error(" Caught MethodArgumentNotValidException "  , ex);
        ApiResponse apiResponse = new ApiResponse();
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        String errorMessage = fieldErrors.stream()
                    .findFirst()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .orElse("Validation error occurred");
        apiResponse.setMessage("Event Not Found");
        apiResponse.setMessage(errorMessage);
        return  new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse> hanldemehtodconstraintviolationexception(ConstraintViolationException ex){
        log.error(" Caught ConstraintViolationException "  , ex);
        ApiResponse apiResponse = new ApiResponse();
        String occurred = ex.getConstraintViolations()
                .stream()
                .findFirst()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .orElse("Validation error occurred");
        apiResponse.setMessage(occurred);
        return  new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception ex) {
        log.error("Caught exception", ex);
        ApiResponse errorDto = new ApiResponse();
        errorDto.setMessage("An unknown error occurred");
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
