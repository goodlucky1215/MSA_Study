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

    final private ModelMapper mapper;

    @Override
    public List<ItemDto> getItemList() {
        List<Item> items = orderRepository.findAll();
        return items.stream().map(i -> mapper.map(i,ItemDto.class)).collect(Collectors.toList());
    }

    @Override
    public Long saveOrderItems(Long memberPkId, List<OrderitemDto> orderitemDtos){
        Member member = memberRepository.findByPkId(memberPkId);
        List<Orderitem> orderitems = new ArrayList<>();
        for(OrderitemDto orderItemDto : orderitemDtos){
            Item item = itemRepository.findByItemId(orderItemDto.getItemId());
            orderitems.add(Orderitem.createOrderitem(item, orderItemDto.getOrderQuantity()));
        }
        Orders order = Orders.createOrder(member, orderitems);
        orderRepository.save(order);
        Long orderId = order.getOrderId();
        return orderId;
    }

    @Override
    public List<MemberOrdersDto> findMemberOrderList(Long pkId) {
        List<Orders> orders = orderRepository.findbyPkId(pkId);
        return orders.stream().map(o -> mapper.map(o, MemberOrdersDto.class)).collect(Collectors.toList());
    }
}
