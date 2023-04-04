package SecondLab.Week5.Main

import akka.actor.{Actor, ActorRef, Props}

import scala.collection.mutable.ArrayBuffer

//case class TweetDetails(tweet: String, score: Int, engagement : Int )
class AggregatorActor(batchPrinter: ActorRef) extends Actor {
  var mapOfTweets = Map[Int, ArrayBuffer[String]]()
  var listOfID = new ArrayBuffer[Int]()

  override def receive: Receive = {
    case message: TweetAndID =>
      if (!listOfID.contains(message.id)) {
        listOfID += message.id
        mapOfTweets += (message.id -> ArrayBuffer("", "", ""))
      }
      mapOfTweets.foreach(element => {
        if (element._1 == message.id) {
          if (sender().path.name == "engagementRatioActor") {
            var existingList = mapOfTweets.getOrElse(message.id, ArrayBuffer())
            existingList(1) = message.tweet
            mapOfTweets += (message.id -> existingList)
          }
          else if (sender().path.name == "sentimentScoreActor") {
            var existingList = mapOfTweets.getOrElse(message.id, ArrayBuffer())
            existingList(2) = message.tweet
            mapOfTweets += (message.id -> existingList)
          }
          else {
            var existingList = mapOfTweets.getOrElse(message.id, ArrayBuffer())
            existingList(0) = message.tweet
            mapOfTweets += (message.id -> existingList)
          }
          if (!element._2.contains("")) {
            batchPrinter ! TweetText(element._1, element._2)
            Thread.sleep(100)
          }
        }
      })

  }
}

object AggregatorActor {
  def props(batchPrinter: ActorRef): Props = Props(new AggregatorActor(batchPrinter))
}