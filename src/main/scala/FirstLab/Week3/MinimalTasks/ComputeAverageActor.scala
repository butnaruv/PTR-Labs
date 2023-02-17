package FirstLab.Week3.MinimalTasks

import akka.actor.{Actor, Props}

class ComputeAverageActor extends Actor{
  var sum : Double= 0
  var average: Double = 0
  def receive = {
    case message: Int =>
      sum = average + message
      average = sum / 2
        println("Current average is : " + average)
  }
}
object ComputeAverageActor {
  def props: Props = Props[ComputeAverageActor]
}
