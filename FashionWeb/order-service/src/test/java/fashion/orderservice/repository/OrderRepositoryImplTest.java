package fashion.orderservice.repository;

import fashion.orderservice.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class OrderRepositoryImplTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EntityManager em;

    private Member member;

    @BeforeEach
    public void sellerUser(){
        Seller seller =  Seller.builder()
                .id("seller").nickname("토끼").passwordEncrypt("1234")
                .build();
        em.persist(seller);

        member = Member.builder()
                .id("god")
                .nickname("아프로디테")
                .password("1234")
                .joinDate(LocalDateTime.now())
                .birth(LocalDate.of(1982, 7, 13))
                .build();
        em.persist(member);
    }

    @DisplayName("아이템 목록이 없을 경우")
    @Test
    public void itemIsEmpty(){
        //given, when
        List<Item> items = orderRepository.findAll();

        //then
        assertEquals(0,items.size());
        assertEquals(new ArrayList<>(),items);

    }

    @DisplayName("아이템 목록 기져오기")
    @Test
    public void itemAllList(){
        //given
        List<Item> getItems = makeItems();
        for(Item i : getItems) em.persist(i);

        // when
        List<Item> items = orderRepository.findAll();

        //then
        assertEquals(2,items.size());
        assertEquals(getItems,items);
    }

    @DisplayName("아이템 주문 성공")
    @Test
    public void itemOrderSuccess(){
        //given
        List<Item> getItems = makeItems();
        for(Item i : getItems) em.persist(i);
        List<Orderitem> getOrderItems = makeOrderItems();
        Orders orders = makeOrders(getOrderItems);

        // when
        orderRepository.save(orders);

        //then
        assertEquals(orders,em.createQuery("select o from Orders o",Orders.class).getResultList().get(0));
    }

    @DisplayName("사용자 주문 목록_없을때_성공")
    @Test
    public void memberOrderList_empty_success(){
        //given

        // when
        List<Orders> orders = orderRepository.findbyPkId(member.getPkId());

        //then
        assertEquals(0,orders.size());

    }

    @DisplayName("사용자 주문 목록_있을때_성공")
    @Test
    public void memberOrderList_success(){
        //given
        List<Item> getItems = makeItems();
        for(Item i : getItems) em.persist(i);
        List<Orderitem> getOrderItems = makeOrderItems();
        Orders order = makeOrders(getOrderItems);
        orderRepository.save(order);
        getOrderItems = makeOrderItems();
        order = makeOrders(getOrderItems);
        orderRepository.save(order);

        // when
        List<Orders> orders = orderRepository.findbyPkId(member.getPkId());

        //then
        assertEquals(2,orders.get(0).getOrderItems().size());
        assertEquals(2,orders.size());

    }

    private List<Item> makeItems(){
        Seller seller = em.createQuery("select i from Seller i",Seller.class).getResultList().get(0);
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

    private List<Orderitem> makeOrderItems() {
        List<Item> items = em.createQuery("select i from Item i",Item.class).getResultList();
        Orderitem orderItem1 = Orderitem.createOrderitem(items.get(0),4L);
        Orderitem orderItem2 = Orderitem.createOrderitem(items.get(1),14L);
        List<Orderitem> orderItems = new ArrayList<>();
        orderItems.add(orderItem1);
        orderItems.add(orderItem2);
        return orderItems;
    }


    private Orders makeOrders(List<Orderitem> orderItems){
        Orders order = Orders.createOrder(member, orderItems);
        return order;
    }

}