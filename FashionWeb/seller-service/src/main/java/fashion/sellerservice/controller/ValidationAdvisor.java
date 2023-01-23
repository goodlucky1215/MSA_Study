package fashion.sellerservice.controller;

import fashion.sellerservice.common.ErrorResult;
import fashion.sellerservice.common.ResultCode;
import fashion.sellerservice.exception.JoinException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ValidationAdvisor {


    @ExceptionHandler(JoinException.class)
    public ResponseEntity<ErrorResult> MethodArgumentNotValidException(JoinException e) {
        ErrorResult errorResult = new ErrorResult(ResultCode.ERROR.getCode(), e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.OK);
    }

}
