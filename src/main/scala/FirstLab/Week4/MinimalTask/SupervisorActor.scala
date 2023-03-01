package FirstLab.Week4.MinimalTask

import akka.actor.SupervisorStrategy.Restart
import akka.actor.{Actor, ActorRef, OneForOneStrategy, Props}

import scala.collection.mutable.ArrayBuffer

case object CreateWorker

case class SendMessage(message: String, index: Int)

case class SendKill(index: Int)

class SupervisorActor extends Actor {
  private val numberOfActors = 4
  val listOfActors = new ArrayBuffer[ActorRef]()
  override val supervisorStrategy = OneForOneStrategy() {
    case _: Exception => Restart
  }

  override def receive: Receive = {
    case CreateWorker =>
      for (i <- 0 until numberOfActors) {
        listOfActors += context.actorOf(WorkerActor.props)
        context.actorOf(WorkerActor.props)
      }
      println(s"${listOfActors.length} of actors are created")

      context.actorOf(WorkerActor.props)
    case SendMessage(message, index) => {
      if (index < listOfActors.length) {
        println(s"Message sent to actor $index")
        listOfActors(index) ! message
      }
      else println(s"Actor $index does not exist")
    }
    case SendKill(index) =>
      println(s"Kill message sent to actor $index")
      listOfActors(index) ! "kill"
  }
}

object SupervisorActor {
  def props: Props = Props[SupervisorActor]
}
