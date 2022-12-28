package fashion.orderservice.repository;

import fashion.orderservice.entity.Item;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class OrderRepositoryImplTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EntityManager em;

    @DisplayName("아이템 목록이 없을 경우")
    @Test
    public void itemIsEmpty(){
        //given, when
        List<Item> items = orderRepository.findAll();

        //then
        assertEquals(0,items.size());
    }
}