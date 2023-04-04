package SecondLab.Week5.Main

import akka.Done
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.Materializer
import akka.stream.scaladsl.{Sink, Source}
import akka.util.ByteString

import scala.concurrent.{ExecutionContext, Future}

case class Start(url: String)

class SSEReader(implicit mat: Materializer, ec: ExecutionContext, listOfActors: List[ActorRef]) extends Actor {
  //val listOfActors = List(ssePrinter, sentimentScore)

  override def receive: Receive = {
    case Start(url) =>
      implicit val classicSystem: akka.actor.ClassicActorSystemProvider = ActorSystem()
      val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = url))
      val source: Future[Done] = responseFuture.flatMap { response =>
        val sourceByteString: Source[ByteString, Any]#Repr[String] = response.entity.dataBytes.map(_.utf8String)
        val sink = Sink.foreach[String] { (tweet: String) =>
          if (!tweet.contains("\"message\": panic")) {
//            val selectedFavouritesCount = "\"favourites_count\":(.*?),".r.findFirstMatchIn(tweet).map(_.group(1)).fold("")(_.toString)
//            val selectedRetweetCount = "\"retweet_count\":(.*?),".r.findFirstMatchIn(tweet).map(_.group(1)).fold("")(_.toString)
//            val selectedFollowersCount = "\"followers_count\":(.*?),".r.findFirstMatchIn(tweet).map(_.group(1)).fold("")(_.toString)
//            val engagementRation = (selectedFavouritesCount.toInt + selectedRetweetCount.toInt) / selectedFollowersCount.toInt
//            val selectTweet = "\"text\":[\\s\\S](.*?),\"source".r
//            val selectedTweet = selectTweet.findFirstMatchIn(tweet).map(_.group(1)).fold("")(_.toString)

            for(element <- listOfActors){
              element ! tweet
            }
//            ssePrinter ! Tweet("From " + self.path.name + " : " + selectedTweet, engagementRation)
//            sentimentScore ! selectedTweet
            Thread.sleep(200)
          }
          else listOfActors(0) ! "kill"
          Thread.sleep(200)

        }
        sourceByteString.runWith(sink)
      }
  }
}

//object SSEReader {
//  def props(implicit mat: Materializer, ec: ExecutionContext, listOfActors: List[ActorRef]): Props =Props(new SSEReader())
//}
object SSEReader {
  def props(implicit mat: Materializer, ec: ExecutionContext, listOfActors: List[ActorRef]): Props =Props(new SSEReader())
}