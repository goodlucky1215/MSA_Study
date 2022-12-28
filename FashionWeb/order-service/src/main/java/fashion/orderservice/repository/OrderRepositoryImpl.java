package fashion.orderservice.repository;

import fashion.orderservice.entity.Item;
import fashion.orderservice.entity.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderRepositoryImpl implements OrderRepository {

    final private EntityManager em;

    @Override
    public List<Item> findAll() {
        return em.createQuery("select i from Item i",Item.class).getResultList();
    }

    @Override
    public void save(Orders order) {
        em.persist(order);
 //       em.persist(order.getOrderItems());
    }

    @Override
    public List<Orders> findbyPkId(Long pkId) {
        return em.createQuery("select o from Orders o where o.member.pkId=:pkId", Orders.class)
                .setParameter("pkId",pkId).getResultList();
    }
}
