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
      println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!! " + counterOfMessages)
      if (counterOfMessages > 5) {
        println("Sending 'Hello, world!' to all printers...")
        listOfActors += context.actorOf(SSEPrinter.props)
        println("Create new actor! the list is " + listOfActors.length)
        //mediatorWorker ! "new Value"
        senderActor ! listOfActors.length
        counterOfMessages = 0
      } else if (counterOfMessages <= 5) {
        listOfActors(listOfActors.length - 1) ! akka.actor.PoisonPill
        //println(listOfActors(listOfActors.length-1))
        listOfActors.remove(listOfActors.length - 1)
        println("I killed an actor: the list is -> " + listOfActors)
        senderActor ! listOfActors.length
      }
  }

  context.system.scheduler.scheduleAtFixedRate(
    initialDelay = 2.seconds,
    interval = 5.seconds,
    receiver = self,
    message = ManagePrinters
  )(context.dispatcher)

}


object PrinterSupervisor {
  def props(): Props = Props[PrinterSupervisor]
}