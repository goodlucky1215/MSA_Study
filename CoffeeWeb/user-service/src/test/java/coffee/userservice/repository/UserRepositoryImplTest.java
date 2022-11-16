package coffee.userservice.repository;

import coffee.userservice.Entity.MemberEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class UserRepositoryImplTest {

    @Autowired
    UserRepository userRepository;

    @PersistenceContext
    EntityManager em;

    @DisplayName("아이디로 로그인 시 정보 있을 때")
    @Test
    public void id_login_true(){
        //given
        MemberEntity memberEntity = MemberEntity.builder()
                .id("id1")
                .nickname("아프로디테")
                .password("1234")
                .join_date(LocalDateTime.now())
                .birth(LocalDate.of(1982, 7, 13))
                .build();
        em.persist(memberEntity);

        //when
        MemberEntity memberInfo = userRepository.findById("id1");

        //then
        assertEquals(memberEntity,memberInfo);
    }

    @DisplayName("이메일로 로그인 시 정보 있을 때")
    @Test
    public void email_login_true(){
        //MemberEntity memberEntity = new MemberEntity("1","","자라","23",null,1,null);
        userRepository.findById("1");
    }

    @DisplayName("아이디로_로그인시_정보_없을때")
    @Test
    public void id_login_false(){
        userRepository.findById("1");
    }

    @DisplayName("이메일로_로그인시_정보_없을때")
    @Test
    public void email_login_false(){
        userRepository.findById("1");
    }
}