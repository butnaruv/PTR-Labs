package SecondLab

import SecondLab.Week1.{SSEPrinter, SSEReader, Start}
import akka.actor.ActorSystem
import akka.stream.Materializer

import scala.concurrent.ExecutionContext.global

object Main extends App {
  {
    val system = ActorSystem("tweetsManipulation")
    implicit val materializer: Materializer = Materializer.createMaterializer(system)
    val ssePrinter = system.actorOf(SSEPrinter.props(), "ssePrinter")
    val sseReader = system.actorOf(SSEReader.props(materializer, global, ssePrinter), "sseReader")
    sseReader ! Start("http://localhost:4000/tweets/1")

  }
}

