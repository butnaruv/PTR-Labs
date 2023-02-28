package FirstLab.Week4

import FirstLab.Week4.MainTask.StringManipulationSupervisor
import FirstLab.Week4.MinimalTask.{CreateWorker, SendKill, SendMessage, SupervisorActor}
import akka.actor.ActorSystem

object Main extends App {
  {
    MinimalTask()
    Thread.sleep(3000)
    MainTask();

  }

  def MinimalTask(): Unit = {
    val system = ActorSystem("Parent-Child-Relation")
    val supervisor = system.actorOf(SupervisorActor.props, "supervisor")
    supervisor ! CreateWorker
    supervisor ! SendMessage("Hello", 1)
    supervisor ! SendMessage("Hello", 6)
    supervisor ! SendKill(1)
    Thread.sleep(1000)
    supervisor ! SendMessage("Noroc", 1)
  }

  def MainTask(): Unit = {
    val system = ActorSystem("StringManipulation")
    val stringManipulationSupervisor = system.actorOf(StringManipulationSupervisor.props, "stringManipulationSupervisor")
    stringManipulationSupervisor ! "I am a real monster"
    Thread.sleep(7000)
    stringManipulationSupervisor ! ""
    Thread.sleep(7000)
    stringManipulationSupervisor ! "I am a reAl monSTer"
  }
}
