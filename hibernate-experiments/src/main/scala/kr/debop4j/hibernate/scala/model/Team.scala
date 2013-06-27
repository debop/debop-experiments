package kr.debop4j.hibernate.scala.model

import javax.persistence._
import org.hibernate.annotations.{LazyCollection, LazyCollectionOption, DynamicInsert, DynamicUpdate}

/**
 * kr.debop4j.hibernate.scala.model.Team 
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 27. 오전 9:01
 */
@Entity
@Table(name = "Team")
@DynamicInsert
@DynamicUpdate
@NamedQueries(Array(
  new NamedQuery(name = "Team.findByName", query = "from Team t where t.name = :name"),
  new NamedQuery(name = "Team.findByNameToMatch", query = "from Team t where t.name like :nameToMatch")
))
class Team {

  @Id
  @GeneratedValue
  var id: Long = _

  var name: String = _

  // One directional 입니다. BiDirectional을 하려면 maeedBy="team" 을 넣고, Buddy class에 team:Team 속성을 추가하세요
  @OneToMany(cascade = Array(CascadeType.ALL), fetch = FetchType.LAZY)
  @LazyCollection(LazyCollectionOption.EXTRA)
  var buddies: java.util.List[Buddy] = new java.util.ArrayList[Buddy]()
}

object Team {

  def apply(name: String): Team = {
    val team = new Team()
    team.name = name
    team
  }
}
