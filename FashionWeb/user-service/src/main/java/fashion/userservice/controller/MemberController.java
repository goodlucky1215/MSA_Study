package fashion.userservice.controller;

import fashion.userservice.dto.MemberInfoDto;
import fashion.userservice.dto.MemberJoinDto;
import fashion.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    final private UserService userService;

    @GetMapping("/hello")
    public String hello(){
        return "hello userservice";
    }

    @PostMapping("/join")
    public String join(@Validated @RequestBody MemberJoinDto userJoinDto){
        if(userService.userJoin(userJoinDto)) return "true";
        return "false";
    }

    @GetMapping("/userInfo")
    public MemberInfoDto userInfo(HttpServletRequest request){
        MemberInfoDto memberInfoDto = userService.getUserInfo(Long.parseLong(request.getHeader("pkId")));
        return memberInfoDto;
    }

    @PostMapping("/nicknamechange")
    public MemberInfoDto nickNameChange(@Validated @RequestBody MemberInfoDto memberInfoDto, HttpServletRequest request){
        MemberInfoDto changeMemberInfoDto = userService.userNicknameChange(memberInfoDto,Long.parseLong(request.getHeader("pkId")));
        return changeMemberInfoDto;
    }


}
