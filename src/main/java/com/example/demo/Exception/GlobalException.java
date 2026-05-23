package com.example.demo.Exception;

import com.example.demo.Dto.ErrorResponseDto;
import com.example.demo.Dto.ValidationDto;
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
    public ResponseEntity<List<ValidationDto>> error(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();

        List<ValidationDto> list = bindingResult.getFieldErrors().stream().map(p -> new ValidationDto(
                p.getDefaultMessage(), p.getField(), p.getRejectedValue())).toList();

        bindingResult.getGlobalErrors().forEach(p -> list.add(new ValidationDto(p.getObjectName(),
                p.getDefaultMessage(),
                null)));
        return ResponseEntity.badRequest().body(list);

    }

    @ExceptionHandler(IncorrectPassword.class)
    public ResponseEntity<?> error(IncorrectPassword ex, ServletWebRequest servletWebRequest) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMessage(ex.getMessage());
        errorResponseDto.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        errorResponseDto.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        errorResponseDto.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RoleNotFound.class)
    public ResponseEntity<?> error(RoleNotFound ex, ServletWebRequest servletWebRequest) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        errorResponseDto.setMessage(ex.getMessage());
        errorResponseDto.setTimeStamp(LocalDateTime.now());
        errorResponseDto.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoleAlreadyExists.class)
    public ResponseEntity<?> error(RoleAlreadyExists ex, ServletWebRequest servletWebRequest) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        errorResponseDto.setMessage(ex.getMessage());
        errorResponseDto.setTimeStamp(LocalDateTime.now());
        errorResponseDto.setStatusCode(HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PermissionAlreadyExists.class)
    public ResponseEntity<?> error(PermissionAlreadyExists ex, ServletWebRequest servletWebRequest) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        errorResponseDto.setMessage(ex.getMessage());
        errorResponseDto.setTimeStamp(LocalDateTime.now());
        errorResponseDto.setStatusCode(HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<?> error(UserNotFound ex, ServletWebRequest servletWebRequest) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        errorResponseDto.setMessage(ex.getMessage());
        errorResponseDto.setTimeStamp(LocalDateTime.now());
        errorResponseDto.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IsEnbaled.class)
    public ResponseEntity<?> error(IsEnbaled ex, ServletWebRequest servletWebRequest) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        errorResponseDto.setMessage(ex.getMessage());
        errorResponseDto.setTimeStamp(LocalDateTime.now());
        errorResponseDto.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidSort.class)
    public ResponseEntity<?> error(InvalidSort ex, ServletWebRequest servletWebRequest) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        errorResponseDto.setMessage(ex.getMessage());
        errorResponseDto.setTimeStamp(LocalDateTime.now());
        errorResponseDto.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(GamesNotFoundException.class)
    public ResponseEntity<?> error(GamesNotFoundException ex, ServletWebRequest servletWebRequest) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        errorResponseDto.setMessage(ex.getMessage());
        errorResponseDto.setTimeStamp(LocalDateTime.now());
        errorResponseDto.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidRole.class)
    public ResponseEntity<?> error(InvalidRole ex, ServletWebRequest servletWebRequest) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        errorResponseDto.setMessage(ex.getMessage());
        errorResponseDto.setTimeStamp(LocalDateTime.now());
        errorResponseDto.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CompanyNotFound.class)
    public ResponseEntity<?> error(CompanyNotFound ex, ServletWebRequest servletWebRequest) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        errorResponseDto.setMessage(ex.getMessage());
        errorResponseDto.setTimeStamp(LocalDateTime.now());
        errorResponseDto.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MarketNotFound.class)
    public ResponseEntity<?> error(MarketNotFound ex, ServletWebRequest servletWebRequest) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        errorResponseDto.setMessage(ex.getMessage());
        errorResponseDto.setTimeStamp(LocalDateTime.now());
        errorResponseDto.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ProductNotFound.class)
    public ResponseEntity<?> error(ProductNotFound ex, ServletWebRequest servletWebRequest) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        errorResponseDto.setMessage(ex.getMessage());
        errorResponseDto.setTimeStamp(LocalDateTime.now());
        errorResponseDto.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }
}