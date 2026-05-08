package com.example.demo.Exception;

import com.example.demo.Dto.ErrorResponse;
import com.example.demo.Dto.ValidationDto;
import com.example.demo.Repository.UserRepository;
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

    private final UserRepository userRepository;

    public GlobalException(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationDto>> error(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<ValidationDto> list = bindingResult.getFieldErrors().stream().map(p ->
                new ValidationDto(p.getDefaultMessage(), p.getField(), p.getRejectedValue())).toList();

        bindingResult.getGlobalErrors().forEach(p -> list.add(new ValidationDto(
                p.getDefaultMessage(), p.getObjectName(), null
        )));
        return ResponseEntity.badRequest().body(list);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> error(BadCredentialsException ex, ServletWebRequest servletWebRequest) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        errorResponse.setTimeStamp(LocalDateTime.now());
        errorResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<?> error(UserAlreadyExists ex, ServletWebRequest servletWebRequest) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        errorResponse.setTimeStamp(LocalDateTime.now());
        errorResponse.setStatusCode(HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<?> error(UserNotFound ex, ServletWebRequest servletWebRequest) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        errorResponse.setTimeStamp(LocalDateTime.now());
        errorResponse.setStatusCode(HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<?> error(InvalidPasswordException ex, ServletWebRequest servletWebRequest) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        errorResponse.setTimeStamp(LocalDateTime.now());
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidSort.class)
    public ResponseEntity<?> error(InvalidSort ex, ServletWebRequest servletWebRequest) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        errorResponse.setTimeStamp(LocalDateTime.now());
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRole.class)
    public ResponseEntity<?> error(InvalidRole ex, ServletWebRequest servletWebRequest) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        errorResponse.setTimeStamp(LocalDateTime.now());
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RestaurantNotFound.class)
    public ResponseEntity<?> error(RestaurantNotFound ex, ServletWebRequest servletWebRequest) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        errorResponse.setTimeStamp(LocalDateTime.now());
        errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MenuItemNotFound.class)
    public ResponseEntity<?> error(MenuItemNotFound ex, ServletWebRequest servletWebRequest) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        errorResponse.setTimeStamp(LocalDateTime.now());
        errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFound.class)
    public ResponseEntity<?> error(OrderNotFound ex, ServletWebRequest servletWebRequest) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        errorResponse.setTimeStamp(LocalDateTime.now());
        errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderItemNotFound.class)
    public ResponseEntity<?> error(OrderItemNotFound ex, ServletWebRequest servletWebRequest) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        errorResponse.setTimeStamp(LocalDateTime.now());
        errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(RewierNotFound.class)
    public ResponseEntity<?> error(RewierNotFound ex, ServletWebRequest servletWebRequest) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        errorResponse.setTimeStamp(LocalDateTime.now());
        errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}