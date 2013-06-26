package kr.debop.hibernate.scala.repository;

import kr.debop.hibernate.scala.HibernateConfig;
import kr.debop4j.data.hibernate.repository.IHibernateDao;
import kr.debop4j.hibernate.scala.model.Buddy;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.fest.assertions.Assertions.assertThat;

/**
 * kr.debop.hibernate.scala.repository.HibernateDaoTest
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 26. 오후 4:17
 */
@Slf4j
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { HibernateConfig.class } )
public class HibernateDaoTest {

    @Autowired
    IHibernateDao dao;

    @Test
    @Transactional( readOnly = true )
    public void setupTest() {
        assertThat(dao).isNotNull();
        assertThat(dao.getSession()).isNotNull();
    }

    @Test
    @Transactional
    public void saveAndDelete() {
        Buddy buddy = new Buddy("성혁", "배");
        dao.save(buddy);
        dao.getSession().flush();
        dao.getSession().clear();

        Buddy loaded = dao.get(Buddy.class, buddy.id());
        assertThat(loaded).isNotNull();
        assertThat(loaded.id()).isEqualTo(buddy.id());
        assertThat(loaded.firstname()).isEqualTo("성혁");
        dao.deleteAll(Buddy.class);
    }
}
