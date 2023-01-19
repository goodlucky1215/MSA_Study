package fashion.sellerservice.repository;

import fashion.sellerservice.entity.Item;
import fashion.sellerservice.entity.Orderitem;
import fashion.sellerservice.entity.Seller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class SellerRepositoryImpl implements SellerRepository{

    private final EntityManager em;

    @Override
    public boolean existsById(String id) {
        return findById(id) == null ? false : true;
    }

    @Override
    public void save(Seller seller) {
        em.persist(seller);
    }

    @Override
    public Seller findBySellerId(Long sellerId) {
        return em.find(Seller.class, sellerId);
    }

    @Override
    public Seller findById(String id) {
        List<Seller> seller = em.createQuery("select s from Seller s where s.id=:id", Seller.class)
                .setParameter("id",id).getResultList();
        return seller.isEmpty() ? null : seller.get(0);
    }

}
