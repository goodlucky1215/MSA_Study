package fashion.userservice.repository;

import fashion.userservice.entity.Member;
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

//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
@Transactional
@Slf4j
class MemberRepositorylmplTest {

    @Autowired
    private MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    @DisplayName("id_회원이 존재할경우")
    @Test
    public void id_notuniqueId(){
        //given
        Member memberEntity1 = Member.builder()
                .id("id1")
                .nickname("아프로디테")
                .password("1234")
                .joinDate(LocalDateTime.now())
                .birth(LocalDate.of(1982, 7, 13))
                .build();
        memberRepository.save(memberEntity1);

        //when
        boolean exitstIdFT = memberRepository.existsById("id1");

        //then
        assertEquals(true,exitstIdFT);

    }

    @DisplayName("id_회원이 존재하지 않을 경우")
    @Test
    public void id_uniqueId(){
        //when
        boolean exitstIdFT = memberRepository.existsById("id1");

        //then
        assertEquals(false,exitstIdFT);

    }

    @DisplayName("email_회원이 존재하는 경우")
    @Test
    public void email_notuniqueId(){
        //given
        Member memberEntity = Member.builder()
                .email("email1@aa.com")
                .nickname("아프로디테")
                .password("1234")
                .joinDate(LocalDateTime.now())
                .birth(LocalDate.of(1982, 7, 13))
                .build();
        memberRepository.save(memberEntity);

        //when
        boolean exitstIdFT = memberRepository.existsByEmail("email1@aa.com");

        //then
        assertEquals(true, exitstIdFT);
    }

    @DisplayName("email_회원이 존재 안하는 경우")
    @Test
    public void email_uniqueId(){
        //when
        boolean exitstIdFT = memberRepository.existsByEmail("email1@aa.com");

        //then
        assertEquals(false, exitstIdFT);
    }


    @DisplayName("id_회원가입_성공")
    @Test
    public void id_save_success(){
        //given
        Member memberEntity1 = Member.builder()
                .id("id1")
                .nickname("아프로디테")
                .password("1234")
                .joinDate(LocalDateTime.now())
                .birth(LocalDate.of(1982, 7, 13))
                .build();

        //when
        memberRepository.save(memberEntity1);
        Member memberEntity = memberRepository.findById("id1");

        //then
        assertEquals(memberEntity1,em.createQuery("select m from Member m where m.id=:id", Member.class)
                .setParameter("id","id1").getResultList().get(0));
        assertEquals("id1",memberEntity.getId());
        assertEquals(null,memberEntity.getEmail());
        assertEquals("아프로디테",memberEntity.getNickname());
        assertEquals(LocalDate.of(1982, 7, 13),memberEntity.getBirth());
        assertEquals("welcome",memberEntity.getGrade());
    }

    @DisplayName("email_회원가입_성공")
    @Test
    public void email_save_success(){
        //given
        Member memberEntity = Member.builder()
                .email("email1@aa.com")
                .nickname("아프로디테")
                .password("1234")
                .joinDate(LocalDateTime.now())
                .birth(LocalDate.of(1982, 7, 13))
                .build();

        //when
        memberRepository.save(memberEntity);

        //then
        assertEquals(memberEntity,em.createQuery("select m from Member m where m.email=:email",
                Member.class).setParameter("email","email1@aa.com").getResultList().get(0));
    }

    @DisplayName("아이디로 로그인 시 정보 있을 때")
    @Test
    public void id_login_true(){
        //given
        Member memberEntity = Member.builder()
                .id("id1")
                .nickname("아프로디테")
                .password("1234")
                .joinDate(LocalDateTime.now())
                .birth(LocalDate.of(1982, 7, 13))
                .build();
        em.persist(memberEntity);

        //when
        Member memberInfo = memberRepository.findById("id1");

        //then
        assertEquals(memberEntity,memberInfo);
    }

    @DisplayName("이메일로 로그인 시 정보 있을 때")
    @Test
    public void email_login_true(){
        //given
        Member memberEntity = Member.builder()
                .email("email1@aa.com")
                .nickname("헤르메스")
                .password("1234")
                .joinDate(LocalDateTime.now())
                .birth(LocalDate.of(1982, 7, 13))
                .build();
        em.persist(memberEntity);

        //when
        Member memberInfo = memberRepository.findByEmail("email1@aa.com");

        //then
        assertEquals(memberEntity,memberInfo);
    }

    @DisplayName("아이디로_로그인시_정보_없을때")
    @Test
    public void id_login_false(){
        //given
        Member memberEntity = Member.builder()
                .id("id1")
                .nickname("아프로디테")
                .password("1234")
                .joinDate(LocalDateTime.now())
                .birth(LocalDate.of(1982, 7, 13))
                .build();
        em.persist(memberEntity);

        //when
        Member memberInfo = memberRepository.findById("id2");

        //then
        assertEquals(null,memberInfo);
    }

    @DisplayName("이메일로_로그인시_정보_없을때")
    @Test
    public void email_login_false(){
        //given
        Member memberEntity = Member.builder()
                .email("email1@aa.com")
                .nickname("헤르메스")
                .password("1234")
                .joinDate(LocalDateTime.now())
                .birth(LocalDate.of(1982, 7, 13))
                .build();
        em.persist(memberEntity);

        //when
        Member memberInfo = memberRepository.findByEmail("email2@aa.com");

        //then
        assertEquals(null,memberInfo);
    }
}