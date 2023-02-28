package FirstLab.Week4.MainTask

import akka.actor.{Actor, Props}

import scala.collection.mutable.ArrayBuffer

class LowercaseMessageActor extends Actor {
  var listOfLowercaseWords = ArrayBuffer[String]()
  var newWord = ""

  println("Second Actor is here!")

  override def receive: Receive = {
    case message: ArrayBuffer[String] =>
      println("2. Am primit mesajul: " + message)
      for (word <- message) {
        for (char <- word.toLowerCase()) {
          if (char == 'm') newWord += 'n'
          else if (char == 'n') newWord += 'm'
          else newWord += char
        }
        listOfLowercaseWords += newWord
        newWord = ""
      }
      println("2. Am returnat mesajul " + listOfLowercaseWords)
      sender ! listOfLowercaseWords
      Thread.sleep(1000)
      listOfLowercaseWords.clear()
  }
}

object LowercaseMessageActor {
  def props: Props = Props[LowercaseMessageActor]
}
