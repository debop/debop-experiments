package kr.debop.scala.test.actors

import scala.actors.Actor


/**
 * kr.debop.scala.test.actors.HiActor 
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 8. 오전 9:58
 */
class HiActor extends Actor {

    def act() {
        while (true) {
            receive {
                case "Hi" => println("Hello")
            }
        }
    }
}
