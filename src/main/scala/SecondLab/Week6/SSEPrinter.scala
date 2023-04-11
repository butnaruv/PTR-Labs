package SecondLab.Week6

import akka.actor.{Actor, ActorRef, Props}

import scala.util.Random

case class ActorReferences(managerActor1: ActorRef, aggregator: ActorRef)

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
  var aggregator1 = sender()
  //  var body = ""
  //  val url = new URL("http://localhost:4000/emotion_values")
  //  val connection = url.openConnection().asInstanceOf[HttpURLConnection]
  //  connection.setRequestMethod("GET")
  //  body = scala.io.Source.fromInputStream(connection.getInputStream).mkString
  //  var myMap = Map[String, Int]()
  //  val splitScores = body.split("\r\n").toList
  //  splitScores.foreach(element => {
  //    val list = element.split("\t")
  //    myMap += (list(0) -> list(1).toInt)
  //  })
  //  connection.disconnect()

  override def receive: Receive = {
    case "kill" => throw new Exception("Something went wrong!")

    case message: TweetAndID =>
      counterOfMessages += 1
      managerActor ! counterOfMessages
      //      var messageToAnalyse = message
      val selectTweet = "\"text\":[\\s\\S](.*?),\"source".r
      var messageToAnalyse = selectTweet.findFirstMatchIn(message.tweet).map(_.group(1)).fold("")(_.toString)
      for (word <- badWords) {
        if (messageToAnalyse.toLowerCase().contains(word)) {
          val indexOfBadWord = messageToAnalyse.toLowerCase().indexOf(word)
          for (i <- indexOfBadWord until (indexOfBadWord + word.length)) {
            messageToAnalyse = messageToAnalyse.updated(i, '*')
          }
        }
      }
      val randomInterval = new Random().nextInt(46) + 5

      aggregator1 ! TweetAndID(messageToAnalyse, message.id)
      Thread.sleep(randomInterval)

    case ActorReferences(theManager: ActorRef, aggregator: ActorRef) =>
      managerActor = theManager
      aggregator1 = aggregator;

    case 0 => counterOfMessages = 0
  }

}

object SSEPrinter {
  def props(): Props = Props[SSEPrinter]
}