package com.example.demo.Exception;

import com.example.demo.DTO.ErrorResponse;
import com.example.demo.DTO.ValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalErrorException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponse>> globalexception(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();

        List<ErrorResponse> list = bindingResult.getFieldErrors()
                .stream().map(c -> new ErrorResponse(c.getDefaultMessage()
                        , c.getField(), c.getRejectedValue())).toList();
        bindingResult.getGlobalErrors()
                .forEach(c -> list.add(new ErrorResponse(
                        c.getDefaultMessage(), c.getObjectName(), null
                )));

        return ResponseEntity.badRequest().body(list);
    }

    @ExceptionHandler(DepartmentAlreadyExistsException.class)
    public ResponseEntity<?> departmentAlreadyExistsex(DepartmentAlreadyExistsException ex, ServletWebRequest servletWebRequest) {
        ValidationError validationError = new ValidationError();
        validationError.setMessage(ex.getMessage());
        validationError.setTimestamp(LocalDateTime.now());
        validationError.setStatusCode(HttpStatus.CONFLICT.value());
        validationError.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        return new ResponseEntity<>(validationError, HttpStatus.CONFLICT);

    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<?> departmentnotfoundexception(DepartmentNotFoundException ex, ServletWebRequest servletWebRequest) {
        ValidationError validationError = new ValidationError();
        validationError.setMessage(ex.getMessage());
        validationError.setTimestamp(LocalDateTime.now());
        validationError.setStatusCode(HttpStatus.NOT_FOUND.value());
        validationError.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        return new ResponseEntity<>(validationError, HttpStatus.NOT_FOUND);


    }

    @ExceptionHandler(UserProfileAlreadyExistsException.class)
    public ResponseEntity<?> userprofileAlreadyexists(UserProfileAlreadyExistsException ex, ServletWebRequest servletWebRequest) {
        ValidationError validationError = new ValidationError();
        validationError.setMessage(ex.getMessage());
        validationError.setTimestamp(LocalDateTime.now());
        validationError.setStatusCode(HttpStatus.CONFLICT.value());
        validationError.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        return new ResponseEntity<>(validationError, HttpStatus.CONFLICT);

    }

    @ExceptionHandler(UserProfileNotFoundException.class)
    public ResponseEntity<?> userprofilenotfoundexception(UserProfileNotFoundException ex,
                                                          ServletWebRequest servletWebRequest) {
        ValidationError validationError = new ValidationError();
        validationError.setMessage(ex.getMessage());
        validationError.setTimestamp(LocalDateTime.now());
        validationError.setStatusCode(HttpStatus.NOT_FOUND.value());
        validationError.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        return new ResponseEntity<>(validationError, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(ProductsNotFoundException.class)
    public ResponseEntity<?> productNotFoundException(ProductsNotFoundException ex,
                                                      ServletWebRequest servletWebRequest) {
        ValidationError validationError = new ValidationError();
        validationError.setMessage(ex.getMessage());
        validationError.setTimestamp(LocalDateTime.now());
        validationError.setStatusCode(HttpStatus.NOT_FOUND.value());
        validationError.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        return new ResponseEntity<>(validationError, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(ProductTypeAlreadyExistsException.class)
    public ResponseEntity<?> productTypeAlreadyexists(ProductTypeAlreadyExistsException ex, ServletWebRequest servletWebRequest) {
        ValidationError validationError = new ValidationError();
        validationError.setMessage(ex.getMessage());
        validationError.setTimestamp(LocalDateTime.now());
        validationError.setStatusCode(HttpStatus.CONFLICT.value());
        validationError.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        return new ResponseEntity<>(validationError, HttpStatus.CONFLICT);

    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<?> productAlreadyExsistsex(ProductAlreadyExistsException ex, ServletWebRequest servletWebRequest) {
        ValidationError validationError = new ValidationError();
        validationError.setMessage(ex.getMessage());
        validationError.setTimestamp(LocalDateTime.now());
        validationError.setStatusCode(HttpStatus.CONFLICT.value());
        validationError.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        return new ResponseEntity<>(validationError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ProductTypeNotFoundException.class)
    public ResponseEntity<?> productTyoeNotFound(ProductTypeNotFoundException ex,
                                                 ServletWebRequest servletWebRequest) {
        ValidationError validationError = new ValidationError();
        validationError.setMessage(ex.getMessage());
        validationError.setTimestamp(LocalDateTime.now());
        validationError.setStatusCode(HttpStatus.NOT_FOUND.value());
        validationError.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        return new ResponseEntity<>(validationError, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> UsernotfoundException(UserNotFoundException ex,
                                                 ServletWebRequest servletWebRequest) {
        ValidationError validationError = new ValidationError();
        validationError.setMessage(ex.getMessage());
        validationError.setTimestamp(LocalDateTime.now());
        validationError.setStatusCode(HttpStatus.NOT_FOUND.value());
        validationError.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        return new ResponseEntity<>(validationError, HttpStatus.NOT_FOUND);
    }
}