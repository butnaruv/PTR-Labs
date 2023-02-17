package FirstLab.Week3.MinimalTasks

import akka.actor._
class AdjustActor extends Actor {
  def receive ={

    case message : String => {
      var adjustedMessage = ""
      for(e <- message) adjustedMessage += e.toLower
      println("Received: " + adjustedMessage)
    }
    case message : Int =>
      println("Received: " + (message.toInt + 1))
    case _ => println("Received : I don ’ t know how to HANDLE this !")  }
}
object AdjustActor {
  def props: Props = Props[AdjustActor]
}
