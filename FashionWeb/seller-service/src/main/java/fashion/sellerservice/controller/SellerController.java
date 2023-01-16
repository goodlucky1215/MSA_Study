package fashion.sellerservice.controller;

import fashion.sellerservice.common.Result;
import fashion.sellerservice.common.ResultCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SellerController {

    @GetMapping("/hello")
    public Result<String> hello(){
        return new Result("hello userservice", ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }

}
