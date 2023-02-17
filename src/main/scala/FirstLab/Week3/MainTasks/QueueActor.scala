package FirstLab.Week3.MainTasks

import akka.actor.{Actor, Props}

import scala.collection.mutable.ArrayBuffer

class QueueActor extends Actor {
  var queue = ArrayBuffer[Int]()

  override def receive: Receive = {
    case message: Int =>
      queue = queue :+ message
      println("ok ")
    case "push" => {
      if (queue.isEmpty) {
        println("No elements in queue")
      }
      else {
        println(queue(0))
        queue.remove(0)
      }
    }
  }
}

object QueueActor {
  def props: Props = Props[QueueActor]

}

