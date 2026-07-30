package com.example.productservice.Exception;

import com.example.productservice.DTO.ErrorResponse;
import com.example.productservice.DTO.Validation;
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
       public ResponseEntity<List<Validation>>error(MethodArgumentNotValidException ex){
        BindingResult bindingResult = ex.getBindingResult();

        List<Validation> list = bindingResult.getFieldErrors().stream().map(l -> new Validation(l.getDefaultMessage(),
                l.getField(), l.getRejectedValue())).toList();

        bindingResult.getGlobalErrors().forEach(p->list.add(new Validation(
                p.getObjectName(),p.getDefaultMessage(),null
        )));

        return ResponseEntity.ok().body(list);
    }

    @ExceptionHandler(MarketNotFound.class)
    public ResponseEntity<?>error(MarketNotFound ex, ServletWebRequest servletWebRequest){
        ErrorResponse response = new ErrorResponse();
        response.setMessage(ex.getMessage());
        response.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        response.setStatusCode(HttpStatus.NOT_FOUND.value());
        response.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ProductNotFound.class)
    public ResponseEntity<?>error(ProductNotFound ex, ServletWebRequest servletWebRequest){
        ErrorResponse response = new ErrorResponse();
        response.setMessage(ex.getMessage());
        response.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        response.setStatusCode(HttpStatus.NOT_FOUND.value());
        response.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
}
