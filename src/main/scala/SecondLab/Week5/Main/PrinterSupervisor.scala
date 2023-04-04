package SecondLab.Week5.Main

import akka.actor.{Actor, ActorRef, OneForOneStrategy, Props, SupervisorStrategy}

import scala.collection.mutable.ArrayBuffer


case object CreatePrinters

case class SendTo(message: String, actorIndex: Int)

class PrinterSupervisor(managerActor: ActorRef, batchActor : ActorRef) extends Actor {
  val listOfActors = new ArrayBuffer[ActorRef]()
  var actorIndex = 0
  var senderActor = sender()

  override val supervisorStrategy = OneForOneStrategy() {
    case _: Exception => SupervisorStrategy.Restart
  }

  override def receive: Receive = {
    case CreatePrinters =>
      managerActor ! ReadyToStart
      for (i <- 0 until 3) {
        listOfActors += context.actorOf(SSEPrinter.props)
        listOfActors(i) ! ActorReferences(managerActor, batchActor)
      }
      println(listOfActors)

    case SendTo(message, actorIndex) =>
      senderActor = sender()
      listOfActors(actorIndex) ! message
      Thread.sleep(100)

    case message: Boolean =>
      if (!message) {
        listOfActors += context.actorOf(SSEPrinter.props)
        println(">>>>>>>>>> Create new actor! the list is of length  " + listOfActors.length + " : " + listOfActors)
        senderActor ! listOfActors.length
      } else if (message && listOfActors.length > 1) {
        listOfActors(listOfActors.length - 1) ! akka.actor.PoisonPill
        listOfActors.remove(listOfActors.length - 1)
        println(">>>>>>>>>> Kill an actor! the list is of length -> " + listOfActors.length + " : " + listOfActors)
        senderActor ! listOfActors.length
      }
      for (i <- listOfActors.indices) {
        listOfActors(i) ! 0
        listOfActors(i) ! managerActor
      }
  }
}

object PrinterSupervisor {
  def props(managerActor: ActorRef, batchActor : ActorRef): Props = Props(new PrinterSupervisor(managerActor, batchActor))
}