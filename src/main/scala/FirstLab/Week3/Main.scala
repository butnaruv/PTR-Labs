package FirstLab.Week3
import akka.actor._

object Main {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("PrinterSystem")
    val printer = system.actorOf(MyFirstActor.props, "printer")
    val printerModifier = system.actorOf(AdjustActor.props, "printerModifier")

//    printer ! "Hello!"
//    printer ! 445
    printerModifier !"Hello"
    printerModifier ! 5
    printerModifier ! List(1, 3)
  }
}

