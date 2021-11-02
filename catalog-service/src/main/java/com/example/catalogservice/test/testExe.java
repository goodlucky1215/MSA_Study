package com.example.catalogservice.test;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class testExe extends Exception {
    String errorCode;

/*    public static String exePro(){
        return errorCode == null ? super.getMessage() : "[" + errorCode + "] " + getMessage();
    }


    @Override
    public static String getMessage() {
        return errorCode == null ? super.getMessage() : "[" + this.errorCode + "] " + super.getMessage();
    }*/
}