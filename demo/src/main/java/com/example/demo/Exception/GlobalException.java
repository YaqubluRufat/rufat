package com.example.demo.Exception;

import com.example.demo.Dto.ErrorResponse;
import com.example.demo.Dto.ValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationError>> error(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();

        List<ValidationError> list = bindingResult.getFieldErrors().stream().map(p -> new ValidationError(p.getField(), p.getDefaultMessage(),
                p.getRejectedValue())).toList();

        bindingResult.getGlobalErrors().forEach(p -> list.add(new ValidationError(p.getDefaultMessage(),
                p.getObjectName(), null)));

        return ResponseEntity.badRequest().body(list);
    }

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<?> error(UserNotFound ex, ServletWebRequest servletWebRequest) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage(ex.getMessage());
        response.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        response.setStatusCode(HttpStatus.NOT_FOUND.value());
        response.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(IncorrectPassword.class)
    public ResponseEntity<?>error(IncorrectPassword ex, ServletWebRequest servletWebRequest){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setTimeStamp(LocalDateTime.now());
        errorResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(errorResponse,HttpStatus.UNAUTHORIZED);
    }


}
