package fashion.orderservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fashion.orderservice.dto.ItemDto;
import fashion.orderservice.dto.MemberOrderItemsDto;
import fashion.orderservice.dto.MemberOrdersDto;
import fashion.orderservice.dto.OrderitemDto;
import fashion.orderservice.entity.Category;
import fashion.orderservice.entity.OrderStatus;
import fashion.orderservice.exception.NotEnoutStockException;
import fashion.orderservice.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        );

        //then
        resultActions
                .andDo(print())
                .andExpect(content().string((new ArrayList<>()).toString()));
        then(orderService).should().getItemList();
    }

    @DisplayName("아이템 목록 가져오기_성공")
    @Test
    public void itemlist_success() throws Exception {
        //given
        List<ItemDto> itemDtos = makeItemsDto();
        given(orderService.getItemList()).willReturn(itemDtos);

        //when
        ResultActions resultActions = mockMvc.perform(
                get("/order-service/itemList")
               .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
        );

        //then
        String expectByItemName = "$.[?(@.itemName == '%s')]";
        String expectByPrice    = "$.[?(@.price == '%s')]";
        String expectByQuantity = "$.[?(@.quantity == '%s')]";
        String expectByCategory   = "$.[?(@.category == '%s')]";
        resultActions
                .andDo(print())
                .andExpect(jsonPath(expectByItemName,"프릴이 달린 니트").exists())
                .andExpect(jsonPath(expectByItemName,"S발란스 운동화").exists())
                .andExpect(jsonPath(expectByPrice,58700).exists())
                .andExpect(jsonPath(expectByPrice,112000L).exists())
                .andExpect(jsonPath(expectByQuantity,32).exists())
                .andExpect(jsonPath(expectByQuantity,15).exists())
                .andExpect(jsonPath(expectByCategory,"top").exists())
                .andExpect(jsonPath(expectByCategory,"shoes").exists());
        then(orderService).should().getItemList();

    }

    @DisplayName("주문하기_재고 없음")
    @Test
    public void order_stuckEmpty() throws Exception {
        //given
        willThrow(new NotEnoutStockException("재고 수량이 부족합니다."))
                .given(orderService).saveOrderItems(1L,getOrderitemDtos());

        //when
        ResultActions resultActions = mockMvc.perform(
                post("/order-service/order")
                .contentType(MediaType.APPLICATION_JSON)
                .header("pkId",1L)
                .content(new ObjectMapper().writeValueAsString(getOrderitemDtos()))
        );

        //then
        resultActions
                .andDo(print())
                .andExpect(jsonPath("code",is("error")))
                .andExpect(jsonPath("message",is("재고 수량이 부족합니다.")));
        then(orderService).should().saveOrderItems(1L,getOrderitemDtos());
    }

    @DisplayName("주문하기_성공")
    @Test
    public void order_success() throws Exception {
        //given
        given(orderService.saveOrderItems(1L,getOrderitemDtos())).willReturn(4L);

        //when
        ResultActions resultActions = mockMvc.perform(
                post("/order-service/order")
                .contentType(MediaType.APPLICATION_JSON)
                .header("pkId",1L)
                .content(new ObjectMapper().writeValueAsString(getOrderitemDtos()))
        );

        //then
        resultActions
                .andDo(print());
        then(orderService).should().saveOrderItems(1L,getOrderitemDtos());
    }

    @DisplayName("사용자 주문 목록 가져오기_주문 목록 0개")
    @Test
    public void memberOrderList_zero() throws Exception {
        //given
        given(orderService.findMemberOrderList(1L)).willReturn(new ArrayList<>());

        //when
        ResultActions resultActions = mockMvc.perform(
                get("/order-service/orderList")
                .header("pkId",1L)
        );

        //then
        resultActions
                .andDo(print())
                .andExpect(content().string((new ArrayList<>()).toString()));
        then(orderService).should().findMemberOrderList(1L);
    }

    @DisplayName("사용자 주문 목록 가져오기_성공")
    @Test
    public void memberOrderList_success() throws Exception {
        //given
        given(orderService.findMemberOrderList(1L)).willReturn(getMemberOrdersDto());

        //when
        ResultActions resultActions = mockMvc.perform(
                get("/order-service/orderList")
                .header("pkId",1L)
        );

        //then
        String expectOrderId = "$.[?(@.orderId == '%s')]";
        String expectByStatus = "$.[?(@.status == '%s')]";
        String expectByItemName    = "$..orderItems[?(@.itemName == '%s')]";
        String expectByCategory    = "$..orderItems[?(@.category == '%s')]";
        resultActions
                .andDo(print())
                .andExpect(jsonPath(expectOrderId,11).exists())
                .andExpect(jsonPath(expectOrderId,31).exists())
                .andExpect(jsonPath(expectByStatus,"ORDER").exists())
                .andExpect(jsonPath(expectByStatus,"CANCEL").exists())
                .andExpect(jsonPath(expectByItemName,"S발란스 운동화").exists())
                .andExpect(jsonPath(expectByItemName,"프릴이 달린 니트").exists())
                .andExpect(jsonPath("$[1].orderItems[0].itemName","S발란스 운동화").exists())
                .andExpect(jsonPath("$[1].orderItems[1].itemName","프릴이 달린 니트").exists())
                .andExpect(jsonPath(expectByCategory,"shoes").exists())
                .andExpect(jsonPath(expectByCategory,"top").exists());

        then(orderService).should().findMemberOrderList(1L);
    }

    private List<ItemDto> makeItemsDto(){
        List<ItemDto> items = new ArrayList<>();

        ItemDto item1 = new ItemDto();
        item1.setItemName("S발란스 운동화");
        item1.setPrice(112000L);
        item1.setQuantity(15L);
        item1.setCategory(Category.shoes);
        ItemDto item2 = new ItemDto();
        item2.setItemName("프릴이 달린 니트");
        item2.setPrice(58700L);
        item2.setQuantity(32L);
        item2.setCategory(Category.top);

        items.add(item1);
        items.add(item2);
        return items;
    }

    private List<OrderitemDto> getOrderitemDtos() {
        List<OrderitemDto> orderitemDtos = new ArrayList<>();
        OrderitemDto orderitemDto1 = new OrderitemDto();
        orderitemDto1.setItemId(1L);
        orderitemDto1.setOrderQuantity(4L);
        OrderitemDto orderitemDto2 = new OrderitemDto();
        orderitemDto2.setItemId(2L);
        orderitemDto2.setOrderQuantity(10L);
        orderitemDtos.add(orderitemDto1);
        orderitemDtos.add(orderitemDto2);
        return orderitemDtos;
    }

    private List<MemberOrdersDto> getMemberOrdersDto() {
        List<MemberOrderItemsDto> orderItems = new ArrayList<>();
        MemberOrderItemsDto memberOrderItemsDto1 = new MemberOrderItemsDto();
        memberOrderItemsDto1.setItemName("S발란스 운동화");
        memberOrderItemsDto1.setOrderQuantity(4L);
        memberOrderItemsDto1.setOrderPrice(4*112000L);
        MemberOrderItemsDto memberOrderItemsDto2 = new MemberOrderItemsDto();
        memberOrderItemsDto2.setItemName("프릴이 달린 니트");
        memberOrderItemsDto2.setOrderQuantity(14L);
        memberOrderItemsDto2.setOrderPrice(14*58700L);
        orderItems.add(memberOrderItemsDto1);
        orderItems.add(memberOrderItemsDto2);

        List<MemberOrdersDto> memberOrdersDtos = new ArrayList<>();

        MemberOrdersDto memberOrdersDto = new MemberOrdersDto();
        memberOrdersDto.setOrderId(11L);
        memberOrdersDto.setStatus(OrderStatus.ORDER);
        memberOrdersDto.setOrderItems(orderItems);
        MemberOrdersDto memberOrdersDto2 = new MemberOrdersDto();
        memberOrdersDto2.setOrderId(31L);
        memberOrdersDto2.setStatus(OrderStatus.CANCEL);
        memberOrdersDto2.setOrderItems(orderItems);

        memberOrdersDtos.add(memberOrdersDto);
        memberOrdersDtos.add(memberOrdersDto2);
        return  memberOrdersDtos;
    }

}