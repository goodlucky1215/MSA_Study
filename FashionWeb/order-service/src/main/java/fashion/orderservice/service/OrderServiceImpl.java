package fashion.orderservice.service;

import fashion.orderservice.dto.ItemDto;
import fashion.orderservice.dto.MemberOrdersDto;
import fashion.orderservice.dto.OrderitemDto;
import fashion.orderservice.entity.Item;
import fashion.orderservice.entity.Member;
import fashion.orderservice.entity.Orderitem;
import fashion.orderservice.entity.Orders;
import fashion.orderservice.repository.ItemRepository;
import fashion.orderservice.repository.MemberRepository;
import fashion.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    final private OrderRepository orderRepository;
    final private MemberRepository memberRepository;
    final private ItemRepository itemRepository;

    @Override
    public List<ItemDto> getItemList() {
        List<Item> items = orderRepository.findAll();
        return items.stream().map(
                i -> new ItemDto(i)
        ).collect(Collectors.toList());
    }

    @Override
    public Long saveOrderItems(Long memberPkId, List<OrderitemDto> orderitemDtos){
        Member member = memberRepository.findByPkId(memberPkId);
        List<Orderitem> orderitems = new ArrayList<>();
        for(OrderitemDto orderItemDto : orderitemDtos){
            Item item = itemRepository.findByItemId(orderItemDto.getItemId());
            log.info("item : 이름 = {} / 가격 = {} / 양 = {} / 번호 = {}", item.getItemName(), item.getPrice(), item.getQuantity(), item.getItemId());
            orderitems.add(Orderitem.createOrderitem(item, orderItemDto.getOrderQuantity()));
        }
        Orders order = Orders.createOrder(member, orderitems);
        orderRepository.save(order);
        log.info("order_service :  주문 번호 = {} / 주문 아이템 이름 = {} / 주문 아이템 갯수 = {}", order.getOrderId(), order.getOrderItems().get(0).getItem().getItemName(), order.getOrderItems().size());
        Long orderId = order.getOrderId();
        return orderId;
    }

    @Override
    public List<MemberOrdersDto> findMemberOrderList(Long pkId) {
        List<Orders> orders = orderRepository.findbyPkId(pkId);
        return orders.stream().map(o -> new MemberOrdersDto(o)).collect(Collectors.toList());
    }
}
