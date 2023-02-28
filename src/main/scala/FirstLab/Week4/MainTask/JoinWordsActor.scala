package FirstLab.Week4.MainTask

import akka.actor.{Actor, Props}

import scala.collection.mutable.ArrayBuffer

class JoinWordsActor extends Actor {
  var result = ""

  println("Third Actor is here!")

  override def receive: Receive = {
    case message: ArrayBuffer[String] =>
      println("3. Am primit mesajul: " + message)

      for (word <- message) {
        if (word != message.last) result = result + word + " "
        else result = result + word
      }
      println("3. Am returnat mesajul " + result)
      Thread.sleep(1000)
      result = ""
  }
}

object JoinWordsActor {
  def props: Props = Props[JoinWordsActor]
}