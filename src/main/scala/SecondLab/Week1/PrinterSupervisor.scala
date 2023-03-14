package SecondLab.Week1

import FirstLab.Week4.MinimalTask.CreateWorker
import akka.actor.{Actor, ActorRef, Props}

import scala.collection.mutable.ArrayBuffer

case object CreatePrinters

class PrinterSupervisor extends Actor {
  val listOfActors = new ArrayBuffer[ActorRef]()
  var actorIndex = 0

  override def receive: Receive = {
    case CreatePrinters =>
      for (i <- 0 until 3) {
        listOfActors += context.actorOf(SSEPrinter.props)
        context.actorOf(SSEPrinter.props)
      }
      println(listOfActors)
    //context.actorOf(SSEPrinter.props)

    case message: String => if (actorIndex < listOfActors.length) {
      listOfActors(actorIndex) ! message
      actorIndex += 1
    } else {
      actorIndex = 0
      listOfActors(actorIndex) ! message
      actorIndex += 1
    }
  }
}

object PrinterSupervisor {
  def props(): Props = Props[PrinterSupervisor]
}
