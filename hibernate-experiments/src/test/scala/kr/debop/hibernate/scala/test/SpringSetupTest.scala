package kr.debop.hibernate.scala.test

import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.ContextConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.hibernate.SessionFactory
import org.junit.{Assert, Test}
import org.springframework.transaction.annotation.Transactional
import kr.debop4j.data.hibernate.repository.IHibernateDao
import kr.debop4j.hibernate.scala.model.Buddy

/**
 * kr.debop.hibernate.scala.test.SpringSetupTest 
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 26. 오후 4:33
 */
@RunWith(classOf[SpringJUnit4ClassRunner])
@ContextConfiguration(classes = Array(classOf[HibernateConfig]))
class SpringSetupTest {

  @Autowired
  var sessionFactory: SessionFactory = _

  @Autowired
  var dao: IHibernateDao = _

  @Test
  @Transactional(readOnly = true)
  def configurationTest() {
    Assert.assertNotNull(sessionFactory)
    val session = sessionFactory.getCurrentSession
    Assert.assertNotNull(session)
  }

  @Test
  @Transactional
  def saveAndDelete() {
    val buddy = new Buddy("성혁", "배")
    dao.save(buddy)
    dao.getSession.flush()
    dao.getSession.clear()

    val loaded = dao.get(classOf[Buddy], buddy.id)
    Assert.assertNotNull(loaded)
    Assert.assertEquals(buddy.id, loaded.id)
    Assert.assertEquals(buddy.firstname, loaded.firstname)

    dao.deleteAll(classOf[Buddy])
  }
}
