package SecondLab.Week6

import akka.actor.{Actor, ActorRef, Props}

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.duration.DurationInt

case object ReadyToStart

case object ManagePrinters

class ManagerActor extends Actor {
  val listOfMessages = new ArrayBuffer[Int]()
  val listOfSenders = new ArrayBuffer[ActorRef]()
  var test = true
  var supervisorReference = sender()

  override def receive: Receive = {
    case ReadyToStart =>  supervisorReference = sender()

    case message: Int => listOfMessages += message
    listOfSenders += sender()

    case ManagePrinters =>
      println("list of messages: " + listOfMessages)
      println("Senders: " + listOfSenders)
      for (element <- listOfMessages) {
        if (element > 4) {
          test = false
        }
      }
      listOfMessages.clear()
      supervisorReference ! test
      test = true

  }

  context.system.scheduler.scheduleAtFixedRate(
    initialDelay = 4.seconds,
    interval = 4.seconds,
    receiver = self,
    message = ManagePrinters
  )(context.dispatcher)
}

object ManagerActor {
  def props: Props = Props[ManagerActor]
}