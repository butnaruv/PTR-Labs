package SecondLab.Week2

import FirstLab.Week4.MinimalTask.SendKill
import akka.actor.{Actor, ActorRef, Props}


class MediatorWorker(printerSupervisor: ActorRef) extends Actor {
  var actorIndex = 0

  override def receive: Receive = {
    case message: String =>
      if (actorIndex < 3) {
        printerSupervisor ! SendTo(message, actorIndex)
        actorIndex += 1
      } else {
        actorIndex = 0
        printerSupervisor ! SendTo(message, actorIndex)
        actorIndex += 1
      }
  }
}

object MediatorWorker {
  def props(printerSupervisor: ActorRef): Props = Props(new MediatorWorker(printerSupervisor))
}
