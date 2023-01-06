package fashion.orderservice.controller;

import fashion.orderservice.dto.ItemDto;
import fashion.orderservice.dto.MemberOrdersDto;
import fashion.orderservice.dto.OrderitemDto;
import fashion.orderservice.service.OrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    //상품 주문
    @PostMapping("order")
    private void saveOrderItems(HttpServletRequest request, List<OrderitemDto> orderItems) {
        orderService.saveOrderItems(Long.parseLong(request.getHeader("pkId")), orderItems);
        return "redirect"
    }

    //주문 목록 조회
    @GetMapping("orderList")
    private List<MemberOrdersDto> findMemberOrderList(HttpServletRequest request{
        return orderService.findMemberOrderList(Long.parseLong(request.getHeader("pkId")));
    }

}
