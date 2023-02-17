package FirstLab.Week3.MinimalTasks.MonitorigActors

import akka.actor.{Actor, ActorRef, Props, Terminated}

class FirstActor(SecondActor : ActorRef) extends Actor{
  context.watch(SecondActor)
  def receive: Receive ={
    case Terminated(SecondActor) =>
      println("I received the message from the Second Actor. It has stopped!")
  }
}
object FirstActor {
  def props(actor: ActorRef): Props = Props(new FirstActor(actor))
}
