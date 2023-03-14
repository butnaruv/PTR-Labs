package SecondLab.Week1

import akka.actor.{Actor, Props}
import akka.actor.FSM.Event

class SSEPrinter extends Actor{
  override def receive: Receive = {
    case message : String => println(message)
  }
}
object SSEPrinter{
  def props() :Props = Props[SSEPrinter]
}