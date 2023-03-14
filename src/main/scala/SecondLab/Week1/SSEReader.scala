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

class SSEReader(implicit mat: Materializer, ec: ExecutionContext, ssePrinter : ActorRef) extends Actor {
  override def receive: Receive = {
    case Start(url) =>
      implicit val classicSystem: akka.actor.ClassicActorSystemProvider = ActorSystem()
      val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = url))
      val source: Future[Done] = responseFuture.flatMap { response =>
        val sourceByteString: Source[ByteString, Any]#Repr[String] = response.entity.dataBytes.map(_.utf8String)
        val sink = Sink.foreach[String] { (tweet: String) =>
//          println(tweet)
            ssePrinter ! tweet
        }
        sourceByteString.runWith(sink)
      }
  }
}

object SSEReader {
  def props(implicit mat: Materializer, ec: ExecutionContext, ssePrinter : ActorRef): Props = Props(new SSEReader())
}
