package FirstLab.Week4.MainTask

import akka.actor.SupervisorStrategy.Restart
import akka.actor.{Actor, ActorRef, AllForOneStrategy, OneForOneStrategy, Props, SupervisorStrategy, Terminated}
import akka.routing.{RoundRobinRoutingLogic, Router}

import scala.collection.mutable.ArrayBuffer

case class restartMe()

class StringManipulationSupervisor extends Actor {
  var processingStringLevel = 0
  val splitMessageActor: ActorRef = context.actorOf(SplitMessageActor.props)
  //  context.watch(splitMessageActor)
  Thread.sleep(1000)
  val lowercaseMessageActor: ActorRef = context.actorOf(LowercaseMessageActor.props)
  //  context.watch(lowercaseMessageActor)
  Thread.sleep(1000)
  val joinWordsActor: ActorRef = context.actorOf(JoinWordsActor.props)
  //  context.watch(joinWordsActor)

  override val supervisorStrategy = AllForOneStrategy() {
    case _: Exception => Restart
  }

  override def receive: Receive = {
    case message: String =>
      if (processingStringLevel == 0) {
        splitMessageActor ! message
        processingStringLevel += 1
      }
    case message: ArrayBuffer[String] =>
      if (processingStringLevel == 1) {
        lowercaseMessageActor ! message
        processingStringLevel += 1
      }
      else if (processingStringLevel == 2) {
        joinWordsActor ! message
        processingStringLevel = 0
      }
    case restartMe => processingStringLevel = 0
  }
}

object StringManipulationSupervisor {
  def props: Props = Props[StringManipulationSupervisor]
}
