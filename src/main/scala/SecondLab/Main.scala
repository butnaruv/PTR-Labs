package SecondLab


import SecondLab.Week1.{SSEPrinter, SSEReader, Start}
import SecondLab.Week2.{CreatePrinters, MediatorWorker, PrinterSupervisor}
import akka.actor.ActorSystem
import akka.stream.Materializer

import scala.concurrent.ExecutionContext.global

object Main extends App {
  {
    val system = ActorSystem("tweetsManipulation")
    implicit val materializer: Materializer = Materializer.createMaterializer(system)
    val printerSupervisor = system.actorOf(PrinterSupervisor.props(), "printerSupervisor")
    val mediatorWorker = system.actorOf(MediatorWorker.props(printerSupervisor), "mediatorWorker")

    //Initialization of actors for first checkpoint
    //    val ssePrinter = system.actorOf(SSEPrinter.props(), "ssePrinter")
    //    val sseReader1 = system.actorOf(SSEReader.props(materializer, global, ssePrinter), "sseReader1")
    //    val sseReader2 = system.actorOf(SSEReader.props(materializer, global, ssePrinter), "sseReader2")

    //Initialization of actors for the second checkpoint, using only supervisor
    //val sseReader1 = system.actorOf(SSEReader.props(materializer, global, printerSupervisor), "sseReader1")
    //val sseReader2 = system.actorOf(SSEReader.props(materializer, global, printerSupervisor), "sseReader2")

    //Initialization of actors for the second checkpoint, using supervisor and mediator
    val sseReader1 = system.actorOf(SSEReader.props(materializer, global, mediatorWorker), "sseReader1")
    val sseReader2 = system.actorOf(SSEReader.props(materializer, global, mediatorWorker), "sseReader2")
    printerSupervisor ! CreatePrinters
    sseReader1 ! Start("http://localhost:4000/tweets/1")
    sseReader2 ! Start("http://localhost:4000/tweets/2")
  }
}

