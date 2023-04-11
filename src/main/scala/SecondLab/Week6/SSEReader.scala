package SecondLab.Week6

import akka.Done
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.Materializer
import akka.stream.scaladsl.{Sink, Source}
import akka.util.ByteString

import scala.concurrent.{ExecutionContext, Future}

case class Start(url: String)
case class TweetAndID(tweet: String, id: Int)
class SSEReader(implicit mat: Materializer, ec: ExecutionContext, id: Int, listOfActors: List[ActorRef]) extends Actor {
  var tweetID = id
  override def receive: Receive = {
    case Start(url) =>
      implicit val classicSystem: akka.actor.ClassicActorSystemProvider = ActorSystem()
      val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = url))
      val source: Future[Done] = responseFuture.flatMap { response =>
        val sourceByteString: Source[ByteString, Any]#Repr[String] = response.entity.dataBytes.map(_.utf8String)
        val sink = Sink.foreach[String] { (tweet: String) =>
          if (!tweet.contains("\"message\": panic")) {
            for (element <- listOfActors) {
              element ! TweetAndID(tweet, tweetID)
            }
            tweetID += 2
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
  def props(implicit mat: Materializer, ec: ExecutionContext, id: Int, listOfActors: List[ActorRef]): Props = Props(new SSEReader())
}