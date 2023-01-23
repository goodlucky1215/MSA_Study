package fashion.sellerservice.repository;

import fashion.sellerservice.entity.Item;
import fashion.sellerservice.entity.Orderitem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository{

    private final EntityManager em;

    @Override
    public List<Map> checkOrderDetails(Long sellerId) {
        /*
            select orderitem.orderitem_id, orderitem.order_quantity, orderitem.order_price, orderitem.order_status,
            item.item_name, orderInfo.id, orderInfo.order_date
            from orderitem
            inner join item on orderitem.item_id = item.item_id
            inner join (select orders.order_id, orders.order_date, member.id from orders inner join member on orders.pk_id = member.pk_id) orderInfo
            on orderitem.order_id = orderInfo.order_id
            where item.seller_id =91;
        */
        return null;
    }

    @Override
    public Orderitem findByOrderitemId(Long orderitemId) {
        return em.find(Orderitem.class, orderitemId);
    }

    @Override
    public void save(Item item) {
        em.persist(item);
    }

    @Override
    public List<Item> findBySellerId(Long sellerId) {
        List<Item> items = em.createQuery("select i from Item i where i.seller.sellerId=:sellerId", Item.class)
                .setParameter("sellerId",sellerId).getResultList();
        return items;
    }

}
