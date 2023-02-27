package FirstLab.Week4.MinimalTask

import akka.actor.SupervisorStrategy.Restart
import akka.actor.{Actor, ActorRef, OneForOneStrategy, Props}

import scala.collection.mutable.ArrayBuffer

case object CreateWorker

case class SendMessage(message: String, index: Int)

case class SendKill(index: Int)

class SupervisorActor extends Actor {
  private var number = 0
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
      println(listOfActors)

      context.actorOf(WorkerActor.props)
    case SendMessage(message, index) => {
      if (index < listOfActors.length) listOfActors(index) ! message
      else println(s"Actor $index does not exist")
    }
    case SendKill(index) => {
      listOfActors(index) ! "kill"
    }
  }
}

object SupervisorActor {
  def props: Props = Props[SupervisorActor]
}
