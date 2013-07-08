package kr.debop.scala.test.concurrency

import scala.actors.Actor
import java.net.{UnknownHostException, InetAddress}

/**
 * kr.debop.scala.test.concurrency.NameResolver 
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 8. 오후 2:08
 */
object NameResolver extends Actor {

  def act() {
    react {
      case (name: String, actor: Actor) =>
        actor ! getIp(name)
        act()
      case "EXIT" =>
        println("Name resolving exiting.")
      //quit
      case msg =>
        println("Unhandled message:" + msg)
        act()
    }
  }

  def getIp(name: String): Option[InetAddress] = {
    println(s"resolve host $name")
    try {
      Some(InetAddress.getByName(name))
    } catch {
      case _: UnknownHostException => None
    }
  }
}
