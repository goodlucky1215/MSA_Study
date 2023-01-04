package fashion.orderservice.repository;


import fashion.orderservice.entity.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
    //아이템 정보
    Item findByItemId(Long itemId);
}
