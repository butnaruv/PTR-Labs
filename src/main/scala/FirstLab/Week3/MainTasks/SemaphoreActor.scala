package FirstLab.Week3.MainTasks

import akka.actor._

class SemaphoreActor(counter: Int) extends Actor {
  var internCounter = counter
  var space = 0
  //  var internQueue = Array()[Int]

  def receive = {
    case "acquireSemaphore" =>
      if (internCounter > 0) {
        internCounter -= 1
        space += 1
        println(s"Semaphore is acquired.")
      } else {
        println("Semaphore should be released! ")
      }
    case "releaseSemaphore" =>
      if (internCounter < counter) {
        internCounter += 1
        println("Semaphore is released")
      } else println("Semaphore is empty. It can not be released")
    case message: Any => println(s"Message received is $message")
  }
}

object SemaphoreActor {
  def props(counter: Int): Props = Props(new SemaphoreActor(counter))
}