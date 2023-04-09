package SecondLab.Week5.Main

import SecondLab.Week5.Main.BatchPrinter.props
import akka.actor.{Actor, Props}

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.duration.DurationInt

case class TweetText(id: Int, listOfDetails: ArrayBuffer[String])

case object PrintBatch

class BatchPrinter(sizeOfBatch: Int, intervalOfPrinting: Int) extends Actor {
  val listOfTweets = new ArrayBuffer[TweetText]()
  var initialIntervalOfPrinting  = intervalOfPrinting
  var startTime = System.nanoTime()
  var elapsedTime: Double = 0.0
  //  var mapOfTweets = Map[Int, List[]]()

  override def receive: Receive = {
    case message: TweetText =>
      if (listOfTweets.length < sizeOfBatch) {
        listOfTweets += message
        println("Size of batch: " + listOfTweets.size)

      } else {
        val currentTime: Long = System.nanoTime()
        elapsedTime = (currentTime - startTime) / 1e9
        PrintBatcher(listOfTweets)
        listOfTweets += message
        startTime = System.nanoTime()
        RestartTimer()
      }
    case PrintBatch =>
      PrintBatcher(listOfTweets)
  }
  RestartTimer()

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
  def RestartTimer(): Unit ={

    context.system.scheduler.scheduleAtFixedRate(
      initialDelay = initialIntervalOfPrinting.seconds,
      interval = (intervalOfPrinting + elapsedTime.toInt).seconds,
      receiver = self,
      message = PrintBatch,
      //   println(initialIntervalOfPrinting)
    )(context.dispatcher)
  }
}

object BatchPrinter {
  def props(sizeOfBatch: Int, intervalOfPrinting: Int): Props = Props(new BatchPrinter(sizeOfBatch, intervalOfPrinting))
}
