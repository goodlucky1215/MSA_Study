package fashion.orderservice;

import fashion.orderservice.entity.Category;
import fashion.orderservice.entity.Item;
import fashion.orderservice.entity.Seller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Transactional
@RequiredArgsConstructor
@Slf4j
public class InitData {

    final private EntityManager em;

   // @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        log.info("test data init");
        Seller seller =  Seller.builder()
                .id("seller").nickname("토끼").passwordEncrypt("1234")
                .build();
        em.persist(seller);
        List<Item> items = new ArrayList<>();
        Item item1 = Item.builder()
                .seller(seller)
                .itemName("S발란스 운동화")
                .price(112000L)
                .quantity(15L)
                .category(Category.shoes)
                .build();
        Item item2 = Item.builder()
                .seller(seller)
                .itemName("프릴이 달린 니트")
                .price(58700L)
                .quantity(32L)
                .category(Category.top)
                .build();
        items.add(item1);
        items.add(item2);
        em.persist(items);
    }

}
