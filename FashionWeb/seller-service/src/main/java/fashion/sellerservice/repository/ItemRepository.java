package fashion.sellerservice.repository;

import fashion.sellerservice.entity.Item;
import fashion.sellerservice.entity.Orderitem;
import fashion.sellerservice.entity.Seller;

import java.util.List;
import java.util.Map;

public interface ItemRepository {

    //고객 주문 목록
    List<Map> memberOrderList(Long sellerId);


    //고객 주문 확인(ORDER => READY)로 변경
    Orderitem findByOrderitemId(Long orderitemId);

    // 상품 등록
    void save(Item item);

    //판매자가 판매하는 상품 목록
    List<Item> findBySellerId(Long sellerId);

}
