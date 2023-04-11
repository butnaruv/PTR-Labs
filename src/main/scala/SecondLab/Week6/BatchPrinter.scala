package SecondLab.Week6

import akka.actor.{Actor, Props}

import java.util.concurrent.Executors
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.ExecutionContext
import scala.concurrent.duration.DurationInt
import scala.util.{Failure, Success}
import slick.jdbc.PostgresProfile.api._

case class TweetText(id: Int, listOfDetails: ArrayBuffer[String])

case object PrintBatch
object PrivateExecutionContext {
  val executor = Executors.newFixedThreadPool(4)
  implicit val exception = ExecutionContext.fromExecutorService(executor)
}


class BatchPrinter(sizeOfBatch: Int, intervalOfPrinting: Int) extends Actor {
  import PrivateExecutionContext._
  val listOfTweets = new ArrayBuffer[TweetText]()
  var initialIntervalOfPrinting  = intervalOfPrinting
  var startTime = System.nanoTime()
  var elapsedTime: Double = 0.0
  //  var mapOfTweets = Map[Int, List[]]()

  override def receive: Receive = {
    case message: TweetText =>
      val tweetForDB = TweetForTable(message.id, message.listOfDetails(0), message.listOfDetails(1), message.listOfDetails(2))
      insertTweet(tweetForDB)
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

  def insertTweet(tweet : TweetForTable) {
    println("A intrat!")
    val insertQuery = DBModel.tweetTable += tweet
    val futureID = Connection.db.run(insertQuery)
    //Connection.db.run(insertQuery)
    futureID.onComplete {
      case Success(newTweetID) => println("New movie " + newTweetID)
      case Failure(exception) => println("New exception " + exception)
    }
    //Thread.sleep(10000)
  }
}

object BatchPrinter {
  def props(sizeOfBatch: Int, intervalOfPrinting: Int): Props = Props(new BatchPrinter(sizeOfBatch, intervalOfPrinting))
}
