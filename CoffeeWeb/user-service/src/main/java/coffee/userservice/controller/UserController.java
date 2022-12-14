package coffee.userservice.controller;

import coffee.userservice.dto.UserInfoDto;
import coffee.userservice.dto.UserJoinDto;
import coffee.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
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
    public UserInfoDto userInfo(ServerHttpRequest request){
        UserInfoDto userInfoDto = userService.getUserInfo(request.getHeaders().get("pkId").toString());
        return userInfoDto;
    }

    @PostMapping("/nicknamechange")
    public UserInfoDto nickNameChange(@RequestBody UserInfoDto userInfoDto){
        UserInfoDto changeUserInfoDto = userService.userNicknameChange(userInfoDto);
        return changeUserInfoDto;
    }


}
