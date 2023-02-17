package FirstLab.Week3.MinimalTasks.MonitorigActors

import akka.actor.{Actor, Props}

class SecondActor extends Actor{
  def receive: Receive = {
    case "start" =>
      println("Monitored actor started")
    case "stop" =>
      context.stop(self)
  }
}
object SecondActor {
  def props: Props = Props[SecondActor]
}
