package kr.debop.hibernate.scala;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.fest.assertions.Assertions.assertThat;

/**
 * kr.debop.hibernate.scala.SpringSetupTest
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 26. 오후 4:12
 */
@Slf4j
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { HibernateConfig.class } )
public class SpringSetupTest {

    @Autowired
    SessionFactory sessionFactory;

    @Test
    public void configrationTest() {
        assertThat(sessionFactory).isNotNull();
        Session session = sessionFactory.openSession();
        assertThat(session).isNotNull();
        session.close();
    }
}
