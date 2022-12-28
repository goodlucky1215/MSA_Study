package fashion.orderservice.repository;

import fashion.orderservice.entity.Item;
import fashion.orderservice.entity.Orders;

import java.util.List;

public interface OrderRepository {
    //상품 목록
    List<Item> findAll();

    //상품 주문
    void save(Orders order);

    //주문 목록 조회
    List<Orders> findbyPkId(Long pkId);

}
