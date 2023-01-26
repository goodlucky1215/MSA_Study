package fashion.sellerservice.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import fashion.sellerservice.entity.QItem;
import fashion.sellerservice.entity.QOrderitem;
import fashion.sellerservice.entity.QSeller;
import fashion.sellerservice.entity.Seller;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static fashion.sellerservice.entity.QSeller.seller;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@Slf4j
class QueryDslTest {

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    EntityManager em;

    JPAQueryFactory query;


    @DisplayName("QueryDsl연습_ver1")
    @Test
    public void queryDsl_ver1(){
        this.query = new JPAQueryFactory(em);

        QSeller seller = QSeller.seller;
        BooleanBuilder builder = new BooleanBuilder();
        String sellerId = "sell";
        if(StringUtils.hasText(sellerId)) {
            builder.and(seller.id.like("%"+sellerId+"%"));
        }

        List<Seller> sellers = query
                .select(seller)
                .from(seller)
                .where(builder)
                .fetch();

        for(Seller result:sellers){
            log.info("id : {} / 회사명 : {}",result.getId(),result.getCompanyName());
        }
    }

    @DisplayName("QueryDsl연습_ver2")
    @Test
    public void queryDsl_ver2(){
        this.query = new JPAQueryFactory(em);

        List<Seller>  sellers = query
                .select(seller)
                .from(seller)
                .where(likeSellerId("sell"))
                .fetch();

        for(Seller result:sellers){
            log.info("id : {} / 회사명 : {}",result.getId(),result.getCompanyName());
        }
    }

    private BooleanExpression likeSellerId(String sellerId){
        if(StringUtils.hasText(sellerId)){
            return seller.id.like("%"+sellerId+"%");
        }
        return null;
    }

    @DisplayName("판매자 물건 주문 정보 가져오기")
    @Test
    public void queryDsl_SellerItemOrderList(){
        this.query = new JPAQueryFactory(em);
        /*
            select orderitem.orderitem_id, orderitem.order_quantity, orderitem.order_price, orderitem.order_status,
            item.item_name, orderInfo.id, orderInfo.order_date
            from orderitem
            inner join item on orderitem.item_id = item.item_id
            inner join (select orders.order_id, orders.order_date, member.id from orders inner join member on orders.pk_id = member.pk_id) orderInfo
            on orderitem.order_id = orderInfo.order_id
            where item.seller_id =91;
        */
        QOrderitem qOrderitem = new QOrderitem();
        QItem qItem = new QItem();
        List<Seller>  sellers = query
                .select(seller)
                .from(qOrderitem)
                .innerJoin(qItem, qItem.itemId)

                .where(likeSellerId("sell"))
                .fetch();

        for(Seller result:sellers){
            log.info("id : {} / 회사명 : {}",result.getId(),result.getCompanyName());
        }
    }


}