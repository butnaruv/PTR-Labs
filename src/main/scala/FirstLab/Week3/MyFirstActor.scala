package FirstLab.Week3
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
