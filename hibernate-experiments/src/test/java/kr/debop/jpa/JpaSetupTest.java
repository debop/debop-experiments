package kr.debop.jpa;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.fest.assertions.Assertions.assertThat;


/**
 * kr.debop.jpa.JpaSetupTest
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 18. 오후 6:00
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
public class JpaSetupTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    public void configurationTest() {
        assertThat(emf).isNotNull();

        EntityManager em = emf.createEntityManager();
        assertThat(em).isNotNull();
        em.close();
    }

}
