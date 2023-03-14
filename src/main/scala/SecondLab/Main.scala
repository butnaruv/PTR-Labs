package SecondLab

import akka.actor.ActorSystem
import akka.stream.{Materializer}

import scala.concurrent.ExecutionContext.global

object Main extends App {
  {
    val system = ActorSystem("Parent-Child-Relation")
    implicit val materializer: Materializer = Materializer.createMaterializer(system)
    val sseReader = system.actorOf(SSEReader.props(materializer, global), "sseReader")
    sseReader ! Start("http://localhost:4000/tweets/1")
  }
}

