package fashion.orderservice.service;

import fashion.orderservice.dto.ItemDto;
import fashion.orderservice.dto.MemberOrderItemsDto;
import fashion.orderservice.dto.MemberOrdersDto;
import fashion.orderservice.dto.OrderitemDto;
import fashion.orderservice.entity.*;
import fashion.orderservice.exception.NotEnoutStockException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//통합테스트
@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    EntityManager em;

    private Seller seller;
    private Member member;
    private Item item;

    @BeforeEach
    public void sellerUser(){
        seller =  Seller.builder()
                .id("seller").companyName("토끼").passwordEncrypt("1234")
                .build();
        em.persist(seller);

        item = Item.builder()
                .seller(seller)
                .itemName("뉴우우 발란스")
                .price(100000L)
                .quantity(14L)
                .category(Category.shoes)
                .build();
        em.persist(item);

        member = Member.builder()
                .id("god")
                .nickname("아프로디테")
                .password("1234")
                .joinDate(LocalDateTime.now())
                .birth(LocalDate.of(1982, 7, 13))
                .build();
        em.persist(member);

    }

    @DisplayName("상품 목록이 없는 경우")
    @Test
    public void itemIsEmpty(){
        //given

        // when
        List<ItemDto> ItemDtos = orderService.getItemList();

        //then
        assertEquals(0,ItemDtos.size());
        assertEquals(new ArrayList<>(),ItemDtos);
    }

    @DisplayName("상품 목록_있는 경우")
    @Test
    public void itemIstrue(){
        //given
        List<Item> item = makeItems();
        for(Item i : item) em.persist(i);

        // when
        List<ItemDto> ItemDtos = orderService.getItemList();

        //then
        assertEquals(2,ItemDtos.size());
        assertEquals("seller",ItemDtos.get(0).getSeller().getId());
        assertEquals("S발란스 운동화",ItemDtos.get(0).getItemName());
    }

    @DisplayName("상품 주문_수량이 없는 경우")
    @Test
    public void itemIsOutOfStock(){
        //given
        List<OrderitemDto> orderitemDtos = new ArrayList<>();
        OrderitemDto orderitemDto = new OrderitemDto();
        orderitemDto.setItemId(item.getItemId());
        orderitemDto.setOrderQuantity(34L);
        orderitemDtos.add(orderitemDto);

        // when
        assertThrows(NotEnoutStockException.class,() -> {
            orderService.saveOrderItems(member.getPkId(),orderitemDtos);
        });
    }


    @DisplayName("상품 주문 완료")
    @Test
    public void itemIsStock(){
        //given
        List<OrderitemDto> orderitemDtos = new ArrayList<>();
        OrderitemDto orderitemDto = new OrderitemDto();
        orderitemDto.setItemId(item.getItemId());
        orderitemDto.setOrderQuantity(4L);
        orderitemDtos.add(orderitemDto);

        //when
        orderService.saveOrderItems(member.getPkId(),orderitemDtos);

        //then
        assertEquals(10,item.getQuantity());


    }

    @DisplayName("주문 목록_없는 경우")
    @Test
    public void memberOrderListIsEmpty(){
        //given

        // when
        List<MemberOrdersDto> orders = orderService.findMemberOrderList(member.getPkId());

        //then
        assertEquals(0,orders.size());
        assertEquals(new ArrayList<>(),orders);
    }

    @DisplayName("주문 목록_있는 경우")
    @Test
    public void memberOrderListIsTrue(){
        List<Orderitem> orderitems = new ArrayList<>();
        Orderitem orderitem = Orderitem.createOrderitem(item,4L);
        orderitems.add(orderitem);
        Orders orders = Orders.createOrder(member, orderitems);
        em.persist(orders);

        // when
        List<MemberOrdersDto> returnOrders = orderService.findMemberOrderList(member.getPkId());

        //then
        assertEquals(1,returnOrders.size());
        assertEquals(1,returnOrders.get(0).getOrderItems().size());
        assertEquals(true,returnOrders.get(0).getStatus().equals(OrderStatus.ORDER));
        //assertEquals(true,returnOrders.get(0).getStatus().equals("ORDER")); => false가 됨
        assertEquals(4,returnOrders.get(0).getOrderItems().get(0).getOrderQuantity());
        assertEquals("뉴우우 발란스",returnOrders.get(0).getOrderItems().get(0).getItemName());
        assertEquals(400000,returnOrders.get(0).getOrderItems().get(0).getOrderPrice());
    }


    private List<Item> makeItems(){
        List<Item> items = new ArrayList<>();
        Item item1 = Item.builder()
                .seller(seller)
                .itemName("S발란스 운동화")

                .price(112000L)
                .quantity(15L)
                .category(Category.shoes)
                .build();
        Item item2 = Item.builder()
                .seller(seller)
                .itemName("프릴이 달린 니트")
                .price(58700L)
                .quantity(32L)
                .category(Category.top)
                .build();
        items.add(item1);
        items.add(item2);
        return items;
    }

}