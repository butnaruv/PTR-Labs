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
    val sseReader1 = system.actorOf(SSEReader.props(materializer, global, ssePrinter), "sseReader1")
    val sseReader2 = system.actorOf(SSEReader.props(materializer, global, ssePrinter), "sseReader2")
    sseReader1 ! Start("http://localhost:4000/tweets/1")
    sseReader2 ! Start("http://localhost:4000/tweets/2")
  }
}

