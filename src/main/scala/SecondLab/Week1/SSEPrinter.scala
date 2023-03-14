package SecondLab.Week1

import akka.actor.{Actor, Props}
import scala.util.Random

class SSEPrinter extends Actor {
  override def receive: Receive = {
    case message: String =>
      val randomInterval = new Random().nextInt(46) + 5
      println("I am " + self.path.name + " printer")
      println(message)
      println("I will sleep for " + randomInterval + " ms.")
      Thread.sleep(randomInterval)
  }
}

object SSEPrinter {
  def props(): Props = Props[SSEPrinter]
}