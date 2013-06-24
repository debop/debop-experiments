package kr.debop.jpa.repository;

import kr.debop.jpa.AppConfig;
import kr.debop.jpa.domain.User;
import kr.debop.jpa.repository.simple.SimpleUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.fest.assertions.Assertions.assertThat;

/**
 * kr.debop.jpa.repository.UserRepositorySample
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 19. 오후 11:12
 */
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { AppConfig.class } )
@Transactional
public class UserRepositorySample {

    @Autowired
    UserRepository repository;

    @Test
    public void testInsert() {
        User user = new User();
        user.setUsername("username");
        user = repository.save(user);

        assertThat(repository.findOne(user.getId())).isEqualTo(user);
    }
}
