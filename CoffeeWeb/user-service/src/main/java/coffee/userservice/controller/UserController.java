package coffee.userservice.controller;

import coffee.userservice.dto.UserInfoDto;
import coffee.userservice.dto.UserJoinDto;
import coffee.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class UserController {

    final private UserService userService;

    @GetMapping("/hello")
    public String hello(){
        return "hello userservice";
    }

    @PostMapping("/join")
    public String join(@Validated @RequestBody UserJoinDto userJoinDto){
        if(userService.userJoin(userJoinDto)) return "true";
        return "false";
    }

    @GetMapping("/userInfo")
    public UserInfoDto userInfo(HttpServletRequest request){
        log.info("get userId {}",request.getHeader("userId"));
        UserInfoDto userInfoDto = userService.getUserInfo(request.getHeader("userId"));
        return userInfoDto;
    }

    @PostMapping("/nicknamechange")
    public UserInfoDto nickNameChange(@RequestBody UserInfoDto userInfoDto){
        UserInfoDto changeUserInfoDto = userService.userNicknameChange(userInfoDto);
        return changeUserInfoDto;
    }


}
