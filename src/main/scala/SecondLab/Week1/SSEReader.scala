package SecondLab.Week1

import akka.Done
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.Materializer
import akka.stream.scaladsl.{Sink, Source}
import akka.util.ByteString

import scala.concurrent.{ExecutionContext, Future}

case class Start(url: String)

class SSEReader(implicit mat: Materializer, ec: ExecutionContext, ssePrinter: ActorRef) extends Actor {
  override def receive: Receive = {
    case Start(url) =>
      implicit val classicSystem: akka.actor.ClassicActorSystemProvider = ActorSystem()
      val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = url))
      val source: Future[Done] = responseFuture.flatMap { response =>
        val sourceByteString: Source[ByteString, Any]#Repr[String] = response.entity.dataBytes.map(_.utf8String)
        val sink = Sink.foreach[String] { (tweet: String) =>
          //
          //          val selectTweet = "\"text\":[\\s\\S](.*?),\"source".r
          //          val selectedTweet = selectTweet.findFirstMatchIn(tweet).map(_.group(1)).fold("")(_.toString)
          //          ssePrinter ! "From " + self.path.name + " : " + selectedTweet
          //println(tweet)
          if(!tweet.contains("\"message\": panic")){
            val selectTweet = "\"text\":[\\s\\S](.*?),\"source".r
            val selectedTweet = selectTweet.findFirstMatchIn(tweet).map(_.group(1)).fold("")(_.toString)
            ssePrinter ! "From " + self.path.name + " : " + selectedTweet
            Thread.sleep(500)
          }
          else ssePrinter ! "kill"
          Thread.sleep(500)
          val selectTweet = "\"text\":[\\s\\S](.*?),\"source".r
          val selectedTweet = selectTweet.findFirstMatchIn(tweet).map(_.group(1)).fold("")(_.toString)
          ssePrinter ! "From " + self.path.name + " : " + selectedTweet
          Thread.sleep(1000)
        }
        sourceByteString.runWith(sink)
      }
  }
}

object SSEReader {
  def props(implicit mat: Materializer, ec: ExecutionContext, ssePrinter: ActorRef): Props = Props(new SSEReader())
}
