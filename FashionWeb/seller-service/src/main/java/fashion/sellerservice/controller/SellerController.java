package fashion.sellerservice.controller;

import fashion.sellerservice.common.Result;
import fashion.sellerservice.common.ResultCode;
import fashion.sellerservice.dto.ItemRegisterDto;
import fashion.sellerservice.dto.OrderDetailsDto;
import fashion.sellerservice.dto.SellerItemListDto;
import fashion.sellerservice.dto.SellerJoinDto;
import fashion.sellerservice.service.SellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping//("seller-service")
@Slf4j
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

    @GetMapping("checkOrderDetails")
    public Result<OrderDetailsDto> memberOrderList(HttpServletRequest request) {
        List<OrderDetailsDto> result = sellerService.checkOrderDetails(Long.parseLong(request.getHeader("sellerId")));
        return new Result(result, ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }

    @PostMapping("memberOrderitemStatus/{orderitemId}")
    public Result<String> memberOrderitemStatus(@PathVariable("orderitemId") Long orderitemId) {
        log.info("orderitemId {}",orderitemId);
        sellerService.changeMemberOrderitemStatus(orderitemId);
        return new Result("고객 주문 확인 변경", ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }

    @PostMapping("saveItem")
    public Result<String> saveItem(HttpServletRequest request, @RequestBody @Validated ItemRegisterDto itemRegisterDto) {
        sellerService.saveItem(Long.parseLong(request.getHeader("sellerId")), itemRegisterDto);
        return new Result("상품 등록 완료", ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }

    @GetMapping("sellerItems")
    public Result<List<SellerItemListDto>> sellerItems(HttpServletRequest request) {
        List<SellerItemListDto> result = sellerService.getSellerItems(Long.parseLong(request.getHeader("sellerId")));
        return new Result(result, ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }
}
