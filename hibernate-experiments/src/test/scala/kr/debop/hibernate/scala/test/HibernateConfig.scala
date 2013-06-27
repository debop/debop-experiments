package kr.debop.hibernate.scala.test

import kr.debop4j.data.hibernate.spring.HSqlConfigBase
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.EnableTransactionManagement
import kr.debop4j.hibernate.scala.model.{Team, Buddy}
import org.springframework.orm.hibernate4.LocalSessionFactoryBean

/**
 * kr.debop.hibernate.scala.test.HibernateConfig 
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 26. 오후 4:34
 */
@Configuration
@EnableTransactionManagement
class HibernateConfig extends HSqlConfigBase {

  // package name으로부터 entity를 조회하는 방식이 scala 인 경우는 java 랑 달라, 검색되지 않네요.
  override def getMappedPackageNames: Array[String] = Array()

  override def setupSessionFactory(factoryBean: LocalSessionFactoryBean) {
    factoryBean.setAnnotatedClasses(Array(classOf[Team], classOf[Buddy]))
  }

}
