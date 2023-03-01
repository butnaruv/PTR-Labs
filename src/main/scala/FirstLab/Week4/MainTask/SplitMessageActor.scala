package FirstLab.Week4.MainTask


import akka.actor.{Actor, Props}

import scala.collection.mutable.ArrayBuffer

class SplitMessageActor extends Actor {
  var string = ""
  var listOfWords = new ArrayBuffer[String]()
  println("First Actor is here!")

  override def receive: Receive = {
    case "" =>
      sender() ! restartMe()
      try throw new Exception("Something went wrong!")
    case message: String =>
      println("1. Am primit mesajul: " + message)
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
      println("1. Am returnat mesajul: " + listOfWords)
      sender ! listOfWords
      Thread.sleep(1000)
      listOfWords.clear()
  }
}

object SplitMessageActor {
  def props: Props = Props[SplitMessageActor]
}
