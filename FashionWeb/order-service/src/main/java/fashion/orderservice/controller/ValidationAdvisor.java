package fashion.orderservice.controller;

import fashion.orderservice.common.ErrorResult;
import fashion.orderservice.common.ResultCode;
import fashion.orderservice.exception.NotEnoutStockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ValidationAdvisor {


    @ExceptionHandler(NotEnoutStockException.class)
    public ResponseEntity<ErrorResult> MethodArgumentNotValidException(NotEnoutStockException e) {
        ErrorResult errorResult = new ErrorResult(ResultCode.ERROR.getCode(), e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.OK);
    }
}
