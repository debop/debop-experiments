package kr.debop4j.hibernate.scala.model

import javax.persistence._
import org.hibernate.annotations.{DynamicUpdate, DynamicInsert}

/**
 * kr.debop4j.hibernate.scala.model.Buddy 
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 26. 오후 3:41
 */
@Entity
@javax.persistence.Table(name = "Buddy")
@DynamicInsert
@DynamicUpdate
@NamedQueries(Array(
  new NamedQuery(name = "Buddy.findByName", query = "from Buddy b where b.firstname = :firstname and b.lastname = :lastname"),
  new NamedQuery(name = "Buddy.findByAge", query = "from Buddy b where b.age = :age")
))
class Buddy {

  def this(first: String, last: String) {
    this()
    this.firstname = first
    this.lastname = last
  }

  @Id
  @GeneratedValue
  @Column(name = "BuddyId")
  var id: Int = _

  @Column(name = "FirstName", length = 64, nullable = false)
  var firstname: String = ""

  @Column(name = "LastName", length = 64, nullable = false)
  var lastname: String = ""

  var age: Int = _

  override def hashCode(): Int = if (id == null) 0 else id.hashCode()

  override def toString: String = getClass.getName + "# Id=" + id + ", first=" + firstname + ", last=" + lastname
}


object Buddy {

  def apply(firstname: String = "", lastname: String = ""): Buddy = {
    val buddy = new Buddy()
    buddy.firstname = firstname
    buddy.lastname = lastname
    return buddy
  }

}