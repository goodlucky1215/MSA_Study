package coffee.userservice.repository;

import coffee.userservice.Entity.MemberEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryImplTest {

    @Autowired
    UserRepository userRepository;

    @DisplayName("아이디로 로그인 시 정보 있을 때")
    @Test
    public void id_login_true(){

        userRepository.findById("1");
    }

    @DisplayName("이메일로 로그인 시 정보 있을 때")
    @Test
    public void email_login_true(){
        MemberEntity memberEntity = new MemberEntity("1","","자라","23",null,1,null);
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