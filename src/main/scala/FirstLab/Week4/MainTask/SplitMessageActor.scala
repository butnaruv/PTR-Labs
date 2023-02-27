package FirstLab.Week4.MainTask


import akka.actor.{Actor, Props}

import scala.collection.mutable.ArrayBuffer

class SplitMessageActor extends Actor {
  var string = ""
  var listOfWords = new ArrayBuffer[String]()
  println("First Actor is here!")

  override def receive: Receive = {
    case "" => throw new Exception("Something went wrong!")
    case message: String =>
      val newMessage = message.trim.replaceAll(" +", " ") + " "
      for (c <- newMessage) {
        if (c != ' ') {
          string += c
        }
        else if (c == ' ') {
          listOfWords += string
          string = ""
        }
      }
      println(listOfWords)
      sender ! listOfWords
  }
}

object SplitMessageActor {
  def props: Props = Props[SplitMessageActor]
}
