package FirstLab.Week4.MinimalTask

import akka.actor.{Actor, Props}

class WorkerActor extends Actor {
  override def receive: Receive = {
    case "kill" => {
      throw new Exception("Something went wrong!")
    }
    case message: String => println("I received " + message)
  }
}

object WorkerActor {
  def props: Props = Props[WorkerActor]
}