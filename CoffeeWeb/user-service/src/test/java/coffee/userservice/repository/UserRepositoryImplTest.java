package coffee.userservice.repository;

import coffee.userservice.Entity.MemberEntity;
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

    @Test
    public void 아이디로_로그인시_정보_맞을때(){
        userRepository.findById("1");
    }

    @Test
    public void 이메일로_로그인시_정보_맞을때(){
        userRepository.findById("1");
    }

    @Test
    public void 아이디로_로그인시_정보_없을때(){
        userRepository.findById("1");
    }

    @Test
    public void 이메일로_로그인시_정보_없을때(){
        userRepository.findById("1");
    }
}