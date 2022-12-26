package fashion.orderservice.repository;

import fashion.orderservice.entity.Item;
import fashion.orderservice.entity.Order;
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
    public void save(Order order) {
        em.persist(order);
        em.persist(order.getOrderItems());
    }

    @Override
    public List<Order> findbyPkId(Long pkId) {
        return em.createQuery("select o from Order o where o.member.pkId=:pkId",Order.class)
                .setParameter("pkId",pkId).getResultList();
    }
}
