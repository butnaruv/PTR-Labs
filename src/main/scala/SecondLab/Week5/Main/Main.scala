package SecondLab.Week5.Main

import akka.actor.ActorSystem
import akka.stream.Materializer

import scala.concurrent.ExecutionContext.global

object Main extends App {
  {
    val system = ActorSystem("tweetsManipulation")
    implicit val materializer: Materializer = Materializer.createMaterializer(system)
    val managerActor = system.actorOf(ManagerActor.props, "managerActor")
    val batchPrinter = system.actorOf(BatchPrinter.props(10), "batchPrinter")
    val printerSupervisor = system.actorOf(PrinterSupervisor.props(managerActor, batchPrinter), "printerSupervisor")
    val mediatorWorker = system.actorOf(MediatorWorker.props(printerSupervisor), "mediatorWorker")
    val sentimentScoreActor = system.actorOf(SentimentScoreActor.props(), "sentimentScoreActor")
    val engagementRatioActor = system.actorOf(EngagementRatioActor.props,"engagementRatioActor" )
    val listOfActors = List(mediatorWorker, sentimentScoreActor, engagementRatioActor)

    val sseReader1 = system.actorOf(SSEReader.props(materializer, global, listOfActors), "sseReader1")
    val sseReader2 = system.actorOf(SSEReader.props(materializer, global, listOfActors), "sseReader2")
    printerSupervisor ! CreatePrinters
    sseReader1 ! Start("http://localhost:4000/tweets/1")
    sseReader2 ! Start("http://localhost:4000/tweets/2")
  }
}
