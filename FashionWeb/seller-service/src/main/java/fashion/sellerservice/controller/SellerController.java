package fashion.sellerservice.controller;

import fashion.sellerservice.common.Result;
import fashion.sellerservice.common.ResultCode;
import fashion.sellerservice.dto.ItemRegisterDto;
import fashion.sellerservice.dto.SellerItemListDto;
import fashion.sellerservice.dto.SellerJoinDto;
import fashion.sellerservice.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class SellerController {

    final private SellerService sellerService;

    @GetMapping("/hello")
    public Result<String> hello() {
        return new Result("hello userservice", ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }

    @PostMapping("joinSeller")
    public Result<Boolean> joinSeller(@RequestBody @Validated SellerJoinDto sellerJoinDto) {
        boolean result = sellerService.joinSeller(sellerJoinDto);
        return new Result(result, ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }

    @GetMapping("memberOrderList")
    public Result<Map> memberOrderList(HttpServletRequest request) {
        List<Map> result = sellerService.getMemberOrderList(Long.parseLong(request.getHeader("pkId")));
        return new Result(result, ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }

    @PostMapping("memberOrderitemStatus")
    public Result<String> memberOrderitemStatus(HttpServletRequest request) {
        sellerService.changeMemberOrderitemStatus(Long.parseLong(request.getHeader("pkId")));
        return new Result("고객 주문 확인 변경", ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }

    @PostMapping("saveItem")
    public Result<String> saveItem(HttpServletRequest request, @RequestBody @Validated ItemRegisterDto itemRegisterDto) {
        sellerService.saveItem(Long.parseLong(request.getHeader("pkId")), itemRegisterDto);
        return new Result("상품 등록 완료", ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }

    @GetMapping("sellerItems")
    public Result<List<SellerItemListDto>> sellerItems(HttpServletRequest request) {
        List<SellerItemListDto> result = sellerService.getSellerItems(Long.parseLong(request.getHeader("pkId")));
        return new Result(result, ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }
}
