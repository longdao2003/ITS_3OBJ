package vn.its.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import vn.its.entity.respone.ResponeAPI;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ApiException.class, ValidationException.class})
    public ResponseEntity<ResponeAPI> handleApiException(Exception e) {
        return ResponseEntity.badRequest().body(ResponeAPI.builder()
        .data(null)
        .message(e.getMessage())
        .build());         
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponeAPI> handleValidException(MethodArgumentNotValidException  e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        // Tạo thông báo lỗi cho từng trường
        String errorMessages = fieldErrors.stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.joining(","));

        // Trả về lỗi với thông điệp chi tiết
        return ResponseEntity.badRequest().body(ResponeAPI.builder()
        .data(null)
        .message(errorMessages)
        .build());   
    }


}
