package SecondLab.Week5.Main

import akka.actor.{Actor, Props}

import scala.collection.mutable.ArrayBuffer

case class BatchTweet(tweet: String)

class BatchPrinter(sizeOfBatch: Int) extends Actor {
  val listOfTweets = new ArrayBuffer[String]()

  override def receive: Receive = {
    case message: String =>
      if (listOfTweets.length < sizeOfBatch) {
        listOfTweets += message
        println("Tweet added!")
        println("Size of batch: " + listOfTweets.size)
      } else {
        println("----------------------Printed Batch----------------------")
        for(tweet <- listOfTweets) println(tweet)
        listOfTweets.clear()
        listOfTweets += message
      }

  }
}
  object BatchPrinter {
    def props(sizeOfBatch: Int): Props = Props(new BatchPrinter(sizeOfBatch))
  }
