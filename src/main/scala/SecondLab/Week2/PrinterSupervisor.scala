package SecondLab.Week2

import SecondLab.Week1.SSEPrinter
import akka.actor.SupervisorStrategy.Restart
import akka.actor.{Actor, ActorRef, OneForOneStrategy, Props}

import scala.collection.mutable.ArrayBuffer

case object CreatePrinters

case object SendKill

case class SendTo(message: String, actorIndex: Int)

class PrinterSupervisor extends Actor {
  val listOfActors = new ArrayBuffer[ActorRef]()
  var actorIndex = 0

  override val supervisorStrategy = OneForOneStrategy() {
    case _: Exception => Restart
  }

  override def receive: Receive = {
    case CreatePrinters =>
      for (i <- 0 until 3) {
        listOfActors += context.actorOf(SSEPrinter.props)
        context.actorOf(SSEPrinter.props)
      }
      println(listOfActors)

    case SendTo(message, actorIndex) => listOfActors(actorIndex) ! message

  }
}

object PrinterSupervisor {
  def props(): Props = Props[PrinterSupervisor]
}
