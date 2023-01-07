package fashion.orderservice.controller;

import fashion.orderservice.dto.ItemDto;
import fashion.orderservice.dto.MemberOrdersDto;
import fashion.orderservice.dto.OrderitemDto;
import fashion.orderservice.service.OrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/order-service")
@RequiredArgsConstructor
public class OrderController {

    final private OrderService orderService;

    @GetMapping("hello")
    public String hello(){
        return "hello orderservice";
    }

    @GetMapping("itemList")
    private List<ItemDto> getItemList(){
        return orderService.getItemList();
    }

    @PostMapping("order")
    private void saveOrderItems(HttpServletRequest request,@Validated @RequestBody List<OrderitemDto> orderItems) {
        orderService.saveOrderItems(Long.parseLong(request.getHeader("pkId")), orderItems);
    }

    @GetMapping("orderList")
    private List<MemberOrdersDto> findMemberOrderList(HttpServletRequest request) {
        return orderService.findMemberOrderList(Long.parseLong(request.getHeader("pkId")));
    }

}
