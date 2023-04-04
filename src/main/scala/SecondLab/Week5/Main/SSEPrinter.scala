package SecondLab.Week5.Main

import akka.actor.{Actor, ActorRef, Props}

import java.net.{HttpURLConnection, URL}
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

case class ActorReferences(managerActor1: ActorRef, batchActor: ActorRef)

class SSEPrinter extends Actor {
  private val badWords: Array[String] = Array(
    "arse", "arsehead", "arsehole", "ass", "asshole", "bastard", "bitch", "bloody", "bollocks", "brotherfucker",
    "bugger", "bullshit", "child-fucker", "Christ on a bike", "Christ on a cracker", "cock", "cocksucker", "crap",
    "cunt", "damn", "damn it", "dick", "dickhead", "dyke", "fatherfucker", "frigger", "fuck", "goddamn", "godsdamn",
    "hell", "holy shit", "horseshit", "in shit", "Jesus Christ", "Jesus fuck", "Jesus H. Christ", "Jesus Harold Christ",
    "Jesus wept", "Jesus, Mary and Joseph", "kike", "motherfucker", "nigga", "nigra", "piss", "prick", "pussy", "shit",
    "shit ass", "shite", "sisterfucker", "slut", "son of a bitch", "son of a whore", "spastic", "sweet Jesus", "turd",
    "twat", "wanker")
  var counterOfMessages = 0
  var managerActor = sender()
  var batcher: ActorRef = sender()
  var body = ""
  val url = new URL("http://localhost:4000/emotion_values")
  val connection = url.openConnection().asInstanceOf[HttpURLConnection]
  connection.setRequestMethod("GET")
  body = scala.io.Source.fromInputStream(connection.getInputStream).mkString
  var myMap = Map[String, Int]()
  val splitScores = body.split("\r\n").toList
  splitScores.foreach(element => {
    val list = element.split("\t")
    myMap += (list(0) -> list(1).toInt)
  })
  connection.disconnect()

  override def receive: Receive = {
    case "kill" => throw new Exception("Something went wrong!")

    case message: String =>
//      var sum = 0
//      var counterOfScore = 0
//      var score = 0
//      val wordsWithScores = new ArrayBuffer[String]()
//      val listOfWordsInMessage = message.tweet.split(" ").toList
//      myMap.foreach(element => {
//        if (listOfWordsInMessage.contains(element._1)) {
//          wordsWithScores += element._1
//          counterOfScore += 1
//          sum += element._2
//        }
//      })
//      if (counterOfScore != 0) {
//        score = sum / counterOfScore
//      }
      counterOfMessages += 1
      managerActor ! counterOfMessages
//      var messageToAnalyse = message
      val selectTweet = "\"text\":[\\s\\S](.*?),\"source".r
      var messageToAnalyse= selectTweet.findFirstMatchIn(message).map(_.group(1)).fold("")(_.toString)
      for (word <- badWords) {
        if (messageToAnalyse.toLowerCase().contains(word)) {
          val indexOfBadWord = messageToAnalyse.toLowerCase().indexOf(word)
          for (i <- indexOfBadWord until (indexOfBadWord + word.length)) {
            messageToAnalyse = messageToAnalyse.updated(i, '*')
          }
        }
      }
      val randomInterval = new Random().nextInt(46) + 5
      batcher ! messageToAnalyse
      //      println("I am " + self.path.name + " printer")
      //      println(messageToAnalyse)
      //      println("SENTIMENT SCORE : " + score)
      //      println("WORDS WITH SCORE: " + wordsWithScores)
      //      println("ENGAGEMENT RATIO: " + message.ratio)
      //      println("I will sleep for " + randomInterval + " ms.")
      Thread.sleep(randomInterval)

    case ActorReferences(theManager: ActorRef, theBatch: ActorRef) =>
      managerActor = theManager
      batcher = theBatch;

    case 0 => counterOfMessages = 0

  }

}

object SSEPrinter {
  def props(): Props = Props[SSEPrinter]
}