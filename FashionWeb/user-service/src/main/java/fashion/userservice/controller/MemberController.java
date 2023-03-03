package fashion.userservice.controller;

import fashion.userservice.common.Result;
import fashion.userservice.common.ResultCode;
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
    public Result<String> hello(){
        return new Result("hello userservice",ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }

    @PostMapping("/join")
    public Result<String> join(@Validated @RequestBody MemberJoinDto userJoinDto){
        if(userService.userJoin(userJoinDto)) return new Result("true",ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
        return new Result("false",ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }

    @GetMapping("/userInfo")
    public Result<MemberInfoDto> userInfo(HttpServletRequest request){
        log.info("userInfo Controller start");
        MemberInfoDto memberInfoDto = userService.getUserInfo(Long.parseLong(request.getHeader("pkId")));
        log.info("userInfo Controller end");
        return new Result(memberInfoDto, ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }

    @PostMapping("/nicknamechange")
    public Result<MemberInfoDto> nickNameChange(@Validated @RequestBody MemberInfoDto memberInfoDto, HttpServletRequest request){
        log.info("nickNameChage Controller");
        MemberInfoDto changeMemberInfoDto = userService.userNicknameChange(memberInfoDto,Long.parseLong(request.getHeader("pkId")));
        return new Result(changeMemberInfoDto,ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMessage());
    }


}
