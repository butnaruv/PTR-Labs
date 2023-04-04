package SecondLab.Week5.Minimal

import akka.actor.{Actor, ActorRef, Props}
case class Tweet(tweet: String, ratio: Int)

class MediatorWorker(printerSupervisor: ActorRef) extends Actor {
  var actorIndex = 0
  var nrOfActors = 3;

  override def receive: Receive = {
    case message: Tweet =>
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
