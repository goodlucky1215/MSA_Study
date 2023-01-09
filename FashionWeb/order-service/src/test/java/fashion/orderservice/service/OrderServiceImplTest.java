package fashion.orderservice.service;

import fashion.orderservice.dto.ItemDto;
import fashion.orderservice.dto.MemberOrderItemsDto;
import fashion.orderservice.dto.MemberOrdersDto;
import fashion.orderservice.dto.OrderitemDto;
import fashion.orderservice.entity.*;
import fashion.orderservice.exception.NotEnoutStockException;
import fashion.orderservice.repository.ItemRepository;
import fashion.orderservice.repository.MemberRepository;
import fashion.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ModelMapper mapper;

    @DisplayName("상품 목록이 없는 경우")
    @Test
    public void itemIsEmpty(){
        //given
        when(orderRepository.findAll()).thenReturn(new ArrayList<>());

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
        when(orderRepository.findAll()).thenReturn(item);
        when(mapper.map(eq(item.get(0)),eq(ItemDto.class))).thenReturn(makeItemsDto().get(0));
        when(mapper.map(eq(item.get(1)),eq(ItemDto.class))).thenReturn(makeItemsDto().get(1));

        // when
        List<ItemDto> ItemDtos = orderService.getItemList();

        //then
        assertEquals(2,ItemDtos.size());
        assertEquals(makeItemsDto(),ItemDtos);
    }

    @DisplayName("상품 주문_수량이 없는 경우")
    @Test
    public void itemIsOutOfStock(){
        //given
        Member memberEntity = Member.builder()
                .pkId(1L)
                .id("id1")
                .nickname("아프로디테")
                .password("1234")
                .joinDate(LocalDateTime.now())
                .birth(LocalDate.of(1982, 7, 13))
                .build();
        List<OrderitemDto> orderitemDtos = getOrderitemDtos();
        List<Item> item = makeItemsStockZero();
        when(memberRepository.findByPkId(1L)).thenReturn(memberEntity);
        when(itemRepository.findByItemId(orderitemDtos.get(0).getItemId())).thenReturn(item.get(0));

        // when
        assertThrows(NotEnoutStockException.class,() -> {
            orderService.saveOrderItems(1L,getOrderitemDtos());
        });

    }


    @DisplayName("상품 주문 완료")
    @Test
    public void itemIsStock(){
        //given
        Member memberEntity = Member.builder()
                .pkId(1L)
                .id("id1")
                .nickname("아프로디테")
                .password("1234")
                .joinDate(LocalDateTime.now())
                .birth(LocalDate.of(1982, 7, 13))
                .build();
        List<OrderitemDto> orderitemDtos = getOrderitemDtos();
        List<Item> item = makeItems();
        when(memberRepository.findByPkId(1L)).thenReturn(memberEntity);
        when(itemRepository.findByItemId(orderitemDtos.get(0).getItemId())).thenReturn(item.get(0));
        when(itemRepository.findByItemId(orderitemDtos.get(1).getItemId())).thenReturn(item.get(1));

        // when
        orderService.saveOrderItems(1L,getOrderitemDtos());

    }

    @DisplayName("주문 목록_없는 경우")
    @Test
    public void memberOrderListIsEmpty(){
        //given
        when(orderRepository.findbyPkId(1L)).thenReturn(new ArrayList<>());

        // when
        List<MemberOrdersDto> orders = orderService.findMemberOrderList(1L);

        //then
        assertEquals(0,orders.size());
        assertEquals(new ArrayList<>(),orders);
    }

    @DisplayName("주문 목록_있는 경우")
    @Test
    public void memberOrderListIsTrue(){
        List<Orders> orders = new ArrayList<>();
        orders.add(makeOrders(makeOrderItems()));
        when(orderRepository.findbyPkId(1L)).thenReturn(orders);
        when(mapper.map(eq(orders.get(0)), eq(MemberOrdersDto.class))).thenReturn(getMemberOrdersDto().get(0));

        // when
        List<MemberOrdersDto> returnOrders = orderService.findMemberOrderList(1L);

        //then
        assertEquals(1,returnOrders.size());
        assertEquals(2,returnOrders.get(0).getOrderItems().size());
        assertEquals(getMemberOrdersDto(),returnOrders);
    }

    private List<Item> makeItems(){
        Seller seller =  Seller.builder()
                .id("seller").companyName("토끼").passwordEncrypt("1234")
                .build();
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

    private List<Item> makeItemsStockZero(){
        Seller seller =  Seller.builder()
                .id("seller").companyName("토끼").passwordEncrypt("1234")
                .build();
        List<Item> items = new ArrayList<>();
        Item item1 = Item.builder()
                .seller(seller)
                .itemName("S발란스 운동화")
                .price(112000L)
                .quantity(1L)
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

    private List<ItemDto> makeItemsDto(){
        List<ItemDto> items = new ArrayList<>();

        ItemDto item1 = new ItemDto();
        item1.setItemName("S발란스 운동화");
        item1.setPrice(112000L);
        item1.setQuantity(15L);
        item1.setCategory(Category.shoes);
        ItemDto item2 = new ItemDto();
        item1.setItemName("프릴이 달린 니트");
        item1.setPrice(58700L);
        item1.setQuantity(32L);
        item1.setCategory(Category.top);

        items.add(item1);
        items.add(item2);
        return items;
    }

    private List<Orderitem> makeOrderItems() {
        Orderitem orderItem1 = Orderitem.createOrderitem(makeItems().get(0),4L);
        Orderitem orderItem2 = Orderitem.createOrderitem(makeItems().get(1),14L);
        List<Orderitem> orderItems = new ArrayList<>();
        orderItems.add(orderItem1);
        orderItems.add(orderItem2);
        return orderItems;
    }

    private Orders makeOrders(List<Orderitem> orderItems){
        Member member = Member.builder().pkId(1L).build();
        Orders order = Orders.createOrder(member, orderItems);
        return order;
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
        memberOrdersDto.setStatus(OrderStatus.ORDER);
        memberOrdersDto.setOrderItems(orderItems);

        memberOrdersDtos.add(memberOrdersDto);
        return  memberOrdersDtos;
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


}