package com.example.marketservice.Exception;

import com.example.marketservice.DTO.ErrorResponse;
import com.example.marketservice.DTO.Validation;
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

        List<Validation> list = bindingResult.getFieldErrors().stream().map(l -> new Validation(l.getObjectName(),
                l.getDefaultMessage(), l.getRejectedValue())).toList();

        ex.getGlobalErrors().forEach(p->list.add(new Validation(
                p.getObjectName(),p.getDefaultMessage(),null
        )));
        return ResponseEntity.badRequest().body(list);
    }
    @ExceptionHandler(MarketNotFound.class)
    public ResponseEntity<?>error(MarketNotFound ex, ServletWebRequest servletWebRequest){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(servletWebRequest.getRequest().getRequestURL().toString());
        errorResponse.setTimeStamp(LocalDateTime.now());
        errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }
}
