package SecondLab.Week5.Main

import akka.actor.{Actor, ActorRef, Props}

import java.net.{HttpURLConnection, URL}
import scala.collection.mutable.ArrayBuffer

class SentimentScoreActor(aggregatorActor: ActorRef) extends Actor{
  val url = new URL("http://localhost:4000/emotion_values")
  val connection = url.openConnection().asInstanceOf[HttpURLConnection]
  connection.setRequestMethod("GET")
  var body = ""
  body = scala.io.Source.fromInputStream(connection.getInputStream).mkString
  var myMap = Map[String, Int]()
  val splitScores = body.split("\r\n").toList
  splitScores.foreach(element => {
    val list = element.split("\t")
    myMap += (list(0) -> list(1).toInt)
  })
  connection.disconnect()
  override def receive: Receive = {
    case message: TweetAndID =>
      var sum = 0
      var counterOfScore = 0
      var score = 0
      val wordsWithScores = new ArrayBuffer[String]()
      val listOfWordsInMessage = message.tweet.split(" ").toList
      myMap.foreach(element => {
        if (listOfWordsInMessage.contains(element._1)) {
          wordsWithScores += element._1
          counterOfScore += 1
          sum += element._2
        }
      })
      if (counterOfScore != 0) {
        score = sum / counterOfScore
      }
      println(self.path.name)
//      println("sentimentScore: " + score)
      //println("Sentiment score: " + message.id)
      aggregatorActor ! TweetAndID(score.toString, message.id)
      Thread.sleep(1000)
  }
}
object SentimentScoreActor {
  def props(aggregatorActor: ActorRef): Props = Props(new SentimentScoreActor(aggregatorActor))
}