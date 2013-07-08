package kr.debop.scala.test.concurrency

import org.junit.Test
import scala.actors.Actor._

/**
 * kr.debop.scala.test.concurrency.NameResolverTest 
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 8. 오후 2:11
 */
class NameResolverTest {

  @Test
  def resolveTest() {
    NameResolver.start()

    val self = actor {
      def act() {
        while (true) {
          receive {
            case x => println(x)
          }
        }
      }
    }
    self.start()

    NameResolver !("www.scala-lang.org", self)

    Thread.sleep(100)
  }

}
