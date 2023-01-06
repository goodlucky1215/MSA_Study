package fashion.orderservice.controller;

import fashion.orderservice.dto.ItemDto;
import fashion.orderservice.dto.OrderitemDto;
import fashion.orderservice.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.test.web.servlet.ResultActions;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OrderService orderService;

    @DisplayName("아이템 목록 가져오기_상품이 0개")
    @Test
    public void itemlist_zero() throws Exception {
        //given
        given(orderService.getItemList()).willReturn(new ArrayList<>());

        //when
        ResultActions resultActions = mockMvc.perform(
                get("/order-service/itemList")
                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
        );

        //then
        resultActions
                .andDo(print())
                .andExpect(content().string((new ArrayList<>()).toString()));
        then(orderService).should().getItemList();
    }
    //@DisplayName("아이템 목록 가져오기_성공")
    @Test
    public void itemlist_success(){
        //given

        //when

        //then
    }
    //@DisplayName("주문하기_재고 없음")
    //@DisplayName("주문하기_성공")
    //@DisplayName("사용자 주문 목록 가져오기_주문 목록 0개")
    //@DisplayName("사용자 주문 목록 가져오기_성공")
/*
    @GetMapping("itemList")
    private List<ItemDto> getItemList(){
        return orderService.getItemList();
    }

    @PostMapping("order")
    private void saveOrderItems(HttpServletRequest request, List<OrderitemDto> orderItems) {
        orderService.saveOrderItems(Long.parseLong(request.getHeader("pkId")), orderItems);
    }

    @GetMapping("orderList")
*/

}