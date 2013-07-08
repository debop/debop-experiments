package kr.debop.scala.test.actors

import scala.actors.Actor._
import org.junit.Test
import scala.actors.{Channel, Actor, OutputChannel}

/**
 * kr.debop.scala.test.actors.ActorTest 
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 8. 오전 10:02
 */
class ActorTest {

  @Test
  def testHiActor() {
    val actor1 = new HiActor
    actor1.start()

    actor1 ! "Hi"
    Thread.sleep(10)
  }

  @Test
  def testAnonymousActor() {
    val actor1 = actor {
      while (true) {
        receive {
          case "Hi" => println("Hello")
        }
      }
    }
    actor1.start()
    actor1 ! "Hi"
    actor1 ! "Hi"
    actor1 ! "Hi"
    actor1 ! "Hi"
    Thread.sleep(10)
  }

  @Test
  def channelTest() {
    val c = new Channel[Int]
    val computerActor: Computer = new Computer
    val input = Seq(1, 2, 3, 4, 5)

    computerActor.start()
    computerActor ! Compute(input, c)

    c.receive {
      case x => println("Sum=" + x)
    }
  }

  @Test
  def reactTest() {
    val c = new Channel[Int]
    val reactActor = new ReactActor
    val input = Seq(1, 2, 3, 4, 5)

    reactActor.start()
    reactActor ! Compute(input, c)

    c.receive {
      case x => println("Sum=" + x)
    }
  }
}

case class Compute(input: Seq[Int], result: OutputChannel[Int])

class Computer extends Actor {
  def act() {
    while (true) {
      receive {
        case Compute(input, out) => {
          val answer = input.sum
          out ! answer
        }
      }
    }
  }
}

class ReactActor extends Actor {
  def act() {
    eventloop {
      case Compute(input, out) => {
        val answer = input.sum
        out ! answer
      }
    }
  }
}
