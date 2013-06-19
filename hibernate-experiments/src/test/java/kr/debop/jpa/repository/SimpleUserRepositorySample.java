package kr.debop.jpa.repository;

import kr.debop.jpa.domain.User;
import kr.debop.jpa.repository.simple.SimpleUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * kr.debop.jpa.repository.SimpleUserRepositorySample
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 19. 오후 10:54
 */
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { SimpleRepositoryConfig.class } )
@Transactional
public class SimpleUserRepositorySample {

    @Autowired
    SimpleUserRepository repository;

    User user;

    @Before
    public void setup() {
        user = new User();
        user.setUsername("foobar");
        user.setFirstname("firstname");
        user.setLastname("lastname");
    }

    @Test
    public void testInsert() {
        user = repository.save(user);
        assertThat(repository.findOne(user.getId())).isEqualTo(user);
    }

    @Test
    public void foo() throws Exception {
        user = repository.save(user);

        List<User> users = repository.findByLastname("lastname");
        assertThat(users).isNotNull();
        assertThat(users.contains(user)).isTrue();
    }

    @Test
    public void tesetname() throws Exception {
        user = repository.save(user);
        List<User> users = repository.findByFirstnameOrLastname("lastname");
        assertThat(users).isNotNull();
        assertThat(users.contains(user)).isTrue();
    }
}
