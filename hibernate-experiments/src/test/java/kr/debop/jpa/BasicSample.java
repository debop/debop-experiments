package kr.debop.jpa;

import kr.debop.jpa.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.fest.assertions.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * kr.debop.jpa.BasicSample
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 19. 오후 10:43
 */
@Slf4j
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { AppConfig.class } )
public class BasicSample {

    @Autowired
    EntityManagerFactory factory;

    private CrudRepository<User, Long> userRepository;
    private EntityManager em;

    @Before
    public void before() {
        em = factory.createEntityManager();
        userRepository = new SimpleJpaRepository<User, Long>(User.class, em);
        em.getTransaction().begin();
    }

    @After
    public void after() {
        em.getTransaction().rollback();
    }

    @Test
    public void savingUsers() {
        User user = new User();
        user.setUsername("username");

        user = userRepository.save(user);
        em.clear();
        Assertions.assertThat(userRepository.findOne(user.getId())).isEqualTo(user);
    }
}
