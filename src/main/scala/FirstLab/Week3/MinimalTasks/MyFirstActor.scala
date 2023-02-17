package FirstLab.Week3.MinimalTasks

import akka.actor._

class MyFirstActor extends Actor {
  def receive = {
    case message: Any =>
      println("I received the message: " + message)
  }
}
object MyFirstActor {
  def props: Props = Props[MyFirstActor]
}
