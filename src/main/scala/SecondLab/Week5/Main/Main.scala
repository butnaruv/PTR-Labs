package SecondLab.Week5.Main

import akka.actor.ActorSystem
import akka.stream.Materializer

import scala.concurrent.ExecutionContext.global

object Main extends App {
  {
    val system = ActorSystem("tweetsManipulation")
    implicit val materializer: Materializer = Materializer.createMaterializer(system)
    val managerActor = system.actorOf(ManagerActor.props, "managerActor")
    val batchPrinter = system.actorOf(BatchPrinter.props(10, 15), "batchPrinter")
    val aggregatorActor = system.actorOf(AggregatorActor.props(batchPrinter), "aggreatorActor")
    val sentimentScoreActor = system.actorOf(SentimentScoreActor.props(aggregatorActor), "sentimentScoreActor")
    val engagementRatioActor = system.actorOf(EngagementRatioActor.props(aggregatorActor), "engagementRatioActor")
    val printerSupervisor = system.actorOf(PrinterSupervisor.props(managerActor, aggregatorActor), "printerSupervisor")
    val mediatorWorker = system.actorOf(MediatorWorker.props(printerSupervisor), "mediatorWorker")
    val listOfActors = List(mediatorWorker, sentimentScoreActor, engagementRatioActor)

    val sseReader1 = system.actorOf(SSEReader.props(materializer, global, 0, listOfActors), "sseReader1")
    val sseReader2 = system.actorOf(SSEReader.props(materializer, global, 1, listOfActors), "sseReader2")
    printerSupervisor ! CreatePrinters
    sseReader1 ! Start("http://localhost:4000/tweets/1")
    sseReader2 ! Start("http://localhost:4000/tweets/2")
  }
}
