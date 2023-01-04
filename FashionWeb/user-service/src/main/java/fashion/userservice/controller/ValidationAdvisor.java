package fashion.userservice.controller;

import fashion.userservice.exception.JoinException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ValidationAdvisor{


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResult> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorResult errorResult = new ErrorResult("error", e.getFieldError().getDefaultMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.OK);
    }

    @ExceptionHandler(JoinException.class)
    public ResponseEntity<ErrorResult> joinException(JoinException e) {
        ErrorResult errorResult = new ErrorResult("error", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.OK);
    }
}
