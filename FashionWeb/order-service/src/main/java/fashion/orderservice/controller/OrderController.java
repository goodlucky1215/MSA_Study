package fashion.orderservice.controller;

import fashion.orderservice.common.Result;
import fashion.orderservice.common.ResultCode;
import fashion.orderservice.dto.ItemDto;
import fashion.orderservice.dto.MemberOrdersDto;
import fashion.orderservice.dto.OrderitemDto;
import fashion.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    final private OrderService orderService;

    @GetMapping("/hello")
    public Result<String> hello(){
        return new Result("hello userservice", ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }

    @GetMapping("itemList")
    private Result<List<ItemDto>> getItemList(){
        return new Result(orderService.getItemList(),ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }

    @PostMapping("order")
    private Result<String> saveOrderItems(HttpServletRequest request,@Validated @RequestBody List<OrderitemDto> orderItems) {
        log.info("orderITems : 아이디 =  {} / 양 = {} ", orderItems.get(0).getItemId(), orderItems.get(0).getOrderQuantity());
        orderService.saveOrderItems(Long.parseLong(request.getHeader("pkId")), orderItems);
        return new Result("주문 성공",ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }

    @GetMapping("orderList")
    private Result<List<MemberOrdersDto>> findMemberOrderList(HttpServletRequest request) {
        return new Result(orderService.findMemberOrderList(Long.parseLong(request.getHeader("pkId")))
                , ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }

}
