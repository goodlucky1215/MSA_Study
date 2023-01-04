package fashion.orderservice.service;

import fashion.orderservice.dto.ItemDto;
import fashion.orderservice.dto.MemberOrdersDto;
import fashion.orderservice.dto.OrderitemDto;

import java.util.List;

public interface OrderService {

    //상품 목록
    List<ItemDto> getItemList();

    //상품 주문
    void saveOrderItems(Long memberPkId, List<OrderitemDto> orderItems);

    //주문 목록 조회
    List<MemberOrdersDto> findMemberOrderList(Long pkId);

}
