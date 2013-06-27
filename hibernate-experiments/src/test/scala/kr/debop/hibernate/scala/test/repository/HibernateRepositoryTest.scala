package kr.debop.hibernate.scala.test.repository

import org.junit.{Assert, Test}
import org.springframework.transaction.annotation.Transactional
import kr.debop4j.hibernate.scala.model.{Team, Buddy}
import org.springframework.beans.factory.annotation.Autowired
import kr.debop4j.data.hibernate.repository.IHibernateDao
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.ContextConfiguration
import kr.debop.hibernate.scala.test.HibernateConfig
import kr.debop4j.data.hibernate.HibernateParameter

/**
 * kr.debop.hibernate.scala.test.repository.HibernateRepositoryTest 
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 26. 오후 6:54
 */
@RunWith(classOf[SpringJUnit4ClassRunner])
@ContextConfiguration(classes = Array(classOf[HibernateConfig]))
class HibernateRepositoryTest {

  @Autowired
  var dao: IHibernateDao = _

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

  @Test
  @Transactional
  def findByNamedQuery() {
    val buddy = new Buddy("성혁", "배")
    dao.save(buddy)
    dao.getSession.flush()
    dao.getSession.clear()

    val loaded = dao.findUniqueByNamedQuery(classOf[Buddy],
                                            "Buddy.findByName",
                                            Array(new HibernateParameter("firstname", "성혁"),
                                                  new HibernateParameter("lastname", "배")): _*)

    Assert.assertNotNull(loaded)
    Assert.assertEquals(buddy.id, loaded.id)
    Assert.assertEquals(buddy.firstname, loaded.firstname)

    dao.deleteAll(classOf[Buddy])
  }

  @Test
  @Transactional
  def oneToManyTest() {

    val team = Team("Team-A")
    val buddy1 = new Buddy("Sunghyouk", "Bae")
    val buddy2 = new Buddy("JeHyoung", "Bae")

    team.buddies.add(buddy1)
    team.buddies.add(buddy2)

    dao.save(team)
    dao.getSession.flush()
    dao.getSession.clear()

    val loaded = dao.findUniqueByNamedQuery(classOf[Team],
                                            "Team.findByName",
                                            new HibernateParameter("name", "Team-A"))

    Assert.assertNotNull(loaded)
    Assert.assertEquals(2, loaded.buddies.size())

    dao.deleteAll(classOf[Team])
    dao.getSession.flush()

    Assert.assertEquals(0, dao.count(classOf[Team]))
    Assert.assertEquals(0, dao.count(classOf[Buddy]))
  }

}
