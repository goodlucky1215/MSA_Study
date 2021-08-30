package com.example.firstservice;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/first-service")
@Slf4j
public class FirstServiceController {
    Environment env;

    @Autowired
    public FirstServiceController(Environment env){
        this.env = env;
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "welcome to the first service";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("first-request") String header){
        log.info(header);
        return "첫번째 : message to the first service";
    }

    @GetMapping("/check")
    public String check(HttpServletRequest request){
        log.info("Server Port={}", request.getServerPort());
        return String.format("안녕, 이건 : message to the first service Port %s",env.getProperty("local.server.port"));
    }
}
