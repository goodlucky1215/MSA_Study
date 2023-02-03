package fashion.sellerservice.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import fashion.sellerservice.dto.OrderDetailsDto;
import fashion.sellerservice.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository{

    private final EntityManager em;

    private final JPAQueryFactory query;

    @Override
    public List<OrderDetailsDto> checkOrderDetails(Long sellerId) {
        QOrderitem qOrderitem = QOrderitem.orderitem;
        QItem qItem = QItem.item;
        QOrders qOrders = QOrders.orders;
        List<OrderDetailsDto> orderitems = query
                .select(Projections.bean(OrderDetailsDto.class,
                        qOrderitem.orderitemId,qOrderitem.orderQuantity,qOrderitem.orderPrice, qOrderitem.orderStatus
                        ,qItem.itemName,qOrders.orderDate, qOrders.member.id))
                .from(qOrderitem)
                .leftJoin(qOrderitem.item, qItem)
                .leftJoin(qOrderitem.order, qOrders)
                .where(likeItemSellerId(sellerId))
                .orderBy(qOrderitem.orderitemId.desc())
                .fetch();
        return orderitems;
    }

    private BooleanExpression likeItemSellerId(Long sellerId){
        QItem qItem = QItem.item;
        return qItem.seller.sellerId.eq(sellerId);
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
