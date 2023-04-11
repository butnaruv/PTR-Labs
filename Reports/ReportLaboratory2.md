# FAF.PTR16.1 -- Project 0

> **Performed by:** Butnaru Valeria, group FAF-201
>
> **Verified by:** asist. univ. Alexandru Osadcenco

## P1W1

**Minimal Task 1** -- Write an actor that would read SSE streams.

```scala
case class Start(url: String)

class SSEReader(implicit mat: Materializer, ec: ExecutionContext) extends Actor {
  override def receive: Receive = {
    case Start(url) =>
      implicit val classicSystem: akka.actor.ClassicActorSystemProvider = ActorSystem()
      val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = url))
      val source: Future[Done] = responseFuture.flatMap { response =>
        val sourceByteString: Source[ByteString, Any]#Repr[String] = response.entity.dataBytes.map(_.utf8String)
        val sink = Sink.foreach[String] { (tweet: String) =>
          println(tweet)
        }
        sourceByteString.runWith(sink)
      }
  }
}

object SSEReader {
  def props(implicit mat: Materializer, ec: ExecutionContext): Props = Props(new SSEReader())
}
```

This code defines an Akka actor called SSEReader which is responsible for reading data from a server-sent events (SSE)
stream. The Start message is used to trigger the start of the SSE stream reading process.

Within the Start case, an HTTP request is made using Akka's Http() method. The response from the server is then
transformed into a Source of ByteString objects, which is then transformed into a Source of String objects using the map
method. Finally, the Source of strings is connected to a Sink which simply prints out each received tweet.

The SSEReader actor can be created using the props method, which takes an implicit Materializer and ExecutionContext.
The Materializer is used to materialize the stream of data, while the ExecutionContext is used to execute the stream
processing operations.
**Minimal Task 2** -- Create an actor that would print on the screen the tweets it receives from
the SSE Reader. You can only print the text of the tweet to save on screen space.

```scala
class SSEPrinter extends Actor {
  override def receive: Receive = {
    case message: String => println(message)
  }
}

object SSEPrinter {
  def props(): Props = Props[SSEPrinter]
}
```
This code defines an Akka actor - SSEPrinter which is responsible for printing out the tweets received from the SSE reader - the actor resposible for reading a stream of data. The receive method is defined to accept a String message, which is printed out to the console.
**Main Task 1** -- Create a second Reader actor that will consume the second stream provided by
the Docker image. Send the tweets to the same Printer actor.


```scala
val sseReader1 = system.actorOf(SSEReader.props(materializer, global, ssePrinter), "sseReader1")
val sseReader2 = system.actorOf(SSEReader.props(materializer, global, ssePrinter), "sseReader2")
sseReader1 ! Start("http://localhost:4000/tweets/1")
sseReader2 ! Start("http://localhost:4000/tweets/2")
```

In order to create a second actor, I created a new instance of the SSEReader actor, sseReader2, the first one having the name of sseReader1, each of which is connected to a single instance of the SSEPrinter actor. The Start message is then sent to both instances of the SSEReader actor, triggering the start of the SSE stream reading process for each instance. The two actors then send a string message to the printer which will print the data.

**Main Task 2** -- Continue your Printer actor. Simulate some load on the actor by sleeping every time a tweet is received. Suggested time of sleep – 5ms to 50ms.

```scala
case message: String =>
  val randomInterval = new Random().nextInt(46) + 5
  println(message)
  println("I will sleep for " + randomInterval + " ms.")
  Thread.sleep(randomInterval)
```

The message handler for the SSEPrinter actor was a littles changed. It accepts a String message likewise, but when the actor receives the proper message, it first generates a random interval between 5 and 50 milliseconds using Random.nextInt(). Then, it prints the message and the duration of the sleep to the console, and pauses the current thread for the duration of the random interval using Thread.sleep()
## P1W2

**Minimal Task 1** -- Create a Worker Pool to substitute the Printer actor from previous week. The
pool will contain 3 copies of the Printer actor which will be supervised by a Pool Supervisor.
Use the one-for-one restart policy

```scala
case object CreatePrinters

class PrinterSupervisor extends Actor {
  val listOfActors = new ArrayBuffer[ActorRef]()
  var actorIndex = 0

  override val supervisorStrategy = OneForOneStrategy() {
    case _: Exception => SupervisorStrategy.Restart
  }
  
  override def receive: Receive = {
    case CreatePrinters =>
      for (i <- 0 until 3) {
        listOfActors += context.actorOf(SSEPrinter.props)
        context.actorOf(SSEPrinter.props)
      }
      println(listOfActors)
    //context.actorOf(SSEPrinter.props)

    case message: String => if (actorIndex < listOfActors.length) {
      listOfActors(actorIndex) ! message
      actorIndex += 1
    } else {
      actorIndex = 0
      listOfActors(actorIndex) ! message
      actorIndex += 1
    }
  }
}

object PrinterSupervisor {
  def props(): Props = Props[PrinterSupervisor]
}
```
Here goes the explanation of the code from above..

```scala
val sseReader1 = system.actorOf(SSEReader.props(materializer, global, printerSupervisor), "sseReader1")
val sseReader2 = system.actorOf(SSEReader.props(materializer, global, printerSupervisor), "sseReader2")
printerSupervisor ! CreatePrinters
```

Here goes the explanation of the code from above..

**Minimal Task 2** -- ICreate an actor that would mediate the tasks being sent to the Worker Pool.
Any tweet that this actor receives will be sent to the Worker Pool in a Round Robin fashion.
Direct the Reader actor to sent it’s tweets to this actor.

```scala
class MediatorWorker(printerSupervisor: ActorRef) extends Actor {
  var actorIndex = 0

  override def receive: Receive = {
    case message: String =>
      if (actorIndex < 3) {
        printerSupervisor ! SendTo(message, actorIndex)
        actorIndex += 1
      } else {
        actorIndex = 0
        printerSupervisor ! SendTo(message, actorIndex)
        actorIndex += 1
      }
  }
}

object MediatorWorker {
  def props(printerSupervisor: ActorRef): Props = Props(new MediatorWorker(printerSupervisor))
}
```

Here goes the explanation of the code from above..

```scala
val mediatorWorker = system.actorOf(MediatorWorker.props(printerSupervisor), "mediatorWorker")
val sseReader1 = system.actorOf(SSEReader.props(materializer, global, mediatorWorker), "sseReader1")
val sseReader2 = system.actorOf(SSEReader.props(materializer, global, mediatorWorker), "sseReader2")
```
Here goes the explanation of the code from above..


**Main Task** -- Continue your Worker actor. Occasionally, the SSE events will contain a “kill
message”. Change the actor to crash when such a message is received. Of course, this should
trigger the supervisor to restart the crashed actor.

```scala
if(!tweet.contains("\"message\": panic")){
            val selectTweet = "\"text\":[\\s\\S](.*?),\"source".r
            val selectedTweet = selectTweet.findFirstMatchIn(tweet).map(_.group(1)).fold("")(_.toString)
            ssePrinter ! "From " + self.path.name + " : " + selectedTweet
            Thread.sleep(500)
          }
          else ssePrinter ! "kill"
```

Here goes the explanation of the code from above.. (code form sseReader)

```scala
 case "kill" => throw new Exception("Something went wrong!")
```
## Conclusion

Here goes your conclusion about this project..

## Bibliography

Here go all links / references to stuff you've used to study for this project..

