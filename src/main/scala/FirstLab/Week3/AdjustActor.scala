package FirstLab.Week3
import akka.actor._

import javax.print.attribute.standard.MediaSize.Other
import scala.reflect.ClassManifestFactory.Int
import scala.reflect.ClassTag.Int
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
