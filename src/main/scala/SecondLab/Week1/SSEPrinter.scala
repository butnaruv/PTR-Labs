package SecondLab.Week1

import akka.actor.{Actor, Props}
import scala.util.Random

class SSEPrinter extends Actor {
  private val badWords: Array[String] = Array(
    "arse", "arsehead", "arsehole", "ass", "asshole", "bastard", "bitch", "bloody", "bollocks", "brotherfucker",
    "bugger", "bullshit", "child-fucker", "Christ on a bike", "Christ on a cracker", "cock", "cocksucker", "crap",
    "cunt", "damn", "damn it", "dick", "dickhead", "dyke", "fatherfucker", "frigger", "fuck", "goddamn", "godsdamn",
    "hell", "holy shit", "horseshit", "in shit", "Jesus Christ", "Jesus fuck", "Jesus H. Christ", "Jesus Harold Christ",
    "Jesus wept", "Jesus, Mary and Joseph", "kike", "motherfucker", "nigga", "nigra", "piss", "prick", "pussy", "shit",
    "shit ass", "shite", "sisterfucker", "slut", "son of a bitch", "son of a whore", "spastic", "sweet Jesus", "turd",
    "twat", "wanker")

  override def receive: Receive = {
    case "kill" => throw new Exception("Something went wrong!")

    case message: String =>
      // var isMessageOk = true
      var messageToAnalyse = message
      for (word <- badWords) {
        if (messageToAnalyse.toLowerCase().contains(word)) {
          //isMessageOk = false
          val indexOfBadWord = messageToAnalyse.toLowerCase().indexOf(word)
          for (i <- indexOfBadWord until (indexOfBadWord + word.length)) {
            messageToAnalyse = messageToAnalyse.updated(i, '*')
          }
          println("FOUND A PROBLEM!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
          println(message)
        }
      }
      val randomInterval = new Random().nextInt(46) + 5
      println("I am " + self.path.name + " printer")
      //      if(isMessageOk){
      //        println(message)
      //      }else
      //        println("Original : " + message)
      println(messageToAnalyse)
      println("I will sleep for " + randomInterval + " ms.")
      Thread.sleep(randomInterval)
    //case _ => throw new Exception("Something went wrong!")
  }
}

object SSEPrinter {
  def props(): Props = Props[SSEPrinter]
}