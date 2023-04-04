package SecondLab.Week5.Main

import SecondLab.Week5.Main.BatchPrinter.props
import akka.actor.{Actor, Props}

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.duration.DurationInt

case class TweetText(id: Int, listOfDetails: ArrayBuffer[String])

case object PrintBatch

class BatchPrinter(sizeOfBatch: Int, intervalOfPrinting: Int) extends Actor {
  val listOfTweets = new ArrayBuffer[TweetText]()
  //  var mapOfTweets = Map[Int, List[]]()

  override def receive: Receive = {
    case message: TweetText =>
      if (listOfTweets.length < sizeOfBatch) {
        listOfTweets += message
        println("Size of batch: " + listOfTweets.size)
      } else {
        PrintBatcher(listOfTweets)
        listOfTweets += message
      }
    case PrintBatch =>
      PrintBatcher(listOfTweets)
  }

  context.system.scheduler.scheduleAtFixedRate(
    initialDelay = intervalOfPrinting.seconds,
    interval = intervalOfPrinting.seconds,
    receiver = self,
    message = PrintBatch
  )(context.dispatcher)

  def PrintBatcher(listOfTweets: ArrayBuffer[TweetText]): Unit = {
    println("----------------------Printed Batch----------------------")
    for (tweet <- listOfTweets) {
      println("-------------------------------------------------------")
      println("Id: " + tweet.id)
      println("LIST OF DETAILS: ")
      println("Tweet: " + tweet.listOfDetails(0))
      println("Engagement Ratio: " + tweet.listOfDetails(1))
      println("Sentiment score: " + tweet.listOfDetails(2))
      println("-------------------------------------------------------")
    }
    listOfTweets.clear()
  }
}

object BatchPrinter {
  def props(sizeOfBatch: Int, intervalOfPrinting: Int): Props = Props(new BatchPrinter(sizeOfBatch, intervalOfPrinting))
}
