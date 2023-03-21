package SecondLab.Week2

import FirstLab.Week4.MinimalTask.SendKill
import SecondLab.Week1.SSEPrinter
import akka.actor.SupervisorStrategy.Restart
import akka.actor.{Actor, ActorRef, OneForOneStrategy, Props, SupervisorStrategy}

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.duration.DurationInt


case object CreatePrinters

case object ManagePrinters

case class SendTo(message: String, actorIndex: Int)

class PrinterSupervisor extends Actor {
  val listOfActors = new ArrayBuffer[ActorRef]()
  var actorIndex = 0
  var counterOfMessages = 0
  var senderActor = sender()

  override val supervisorStrategy = OneForOneStrategy() {
    case _: Exception => SupervisorStrategy.Restart
  }

  override def receive: Receive = {
    case CreatePrinters =>
      for (i <- 0 until 3) {
        listOfActors += context.actorOf(SSEPrinter.props)
      }
      println(listOfActors)
    case SendTo(message, actorIndex) =>
      counterOfMessages += 1
      senderActor = sender()
      listOfActors(actorIndex) ! message
    case ManagePrinters =>
      println(">>>>>>>>>> Number of messages sent in last 5 seconds: " + counterOfMessages)
      if (counterOfMessages > 30) {
        listOfActors += context.actorOf(SSEPrinter.props)
        println(">>>>>>>>>> Create new actor! the list is of length  " + listOfActors.length + " : " + listOfActors)
        senderActor ! listOfActors.length
        counterOfMessages = 0
      } else if (counterOfMessages < 30 && listOfActors.length > 1) {
        listOfActors(listOfActors.length - 1) ! akka.actor.PoisonPill
        listOfActors.remove(listOfActors.length - 1)
        println(">>>>>>>>>> Kill an actor! the list is of length -> " + listOfActors.length + " : " + listOfActors)
        senderActor ! listOfActors.length
      }
  }

  context.system.scheduler.scheduleAtFixedRate(
    initialDelay = 2.seconds,
    interval = 4.seconds,
    receiver = self,
    message = ManagePrinters
  )(context.dispatcher)

}


object PrinterSupervisor {
  def props(): Props = Props[PrinterSupervisor]
}