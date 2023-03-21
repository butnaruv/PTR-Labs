package SecondLab.Week2

import FirstLab.Week4.MinimalTask.SendKill
import akka.actor.{Actor, ActorRef, Props}


class MediatorWorker(printerSupervisor: ActorRef) extends Actor {
  var actorIndex = 0
  var nrOfActors = 3;

  override def receive: Receive = {
    case message: String =>
      if (actorIndex < nrOfActors) {
        printerSupervisor ! SendTo(message, actorIndex)
        actorIndex += 1
      } else {
        actorIndex = 0
        printerSupervisor ! SendTo(message, actorIndex)
        actorIndex += 1
      }
    case message: Int =>
      nrOfActors = message
      println("New nr of actors! " + nrOfActors)
  }
}

object MediatorWorker {
  def props(printerSupervisor: ActorRef): Props = Props(new MediatorWorker(printerSupervisor))
}
