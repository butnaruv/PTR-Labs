package FirstLab.Week3


import FirstLab.Week3.MainTasks.QueueActor
import FirstLab.Week3.MinimalTasks.MonitorigActors.{FirstActor, SecondActor}
import FirstLab.Week3.MinimalTasks.{AdjustActor, ComputeAverageActor, MyFirstActor}
import akka.actor._

object Main {
  def main(args: Array[String]): Unit = {
    println("--- Minimal Tasks ---")
    MinimalTask1()
    MinimalTask2()
    MinimalTask3()
    MinimalTask4()
    println("--- Main Tasks ---")
    MainTask1()

  }


  def MinimalTask1(): Unit = {
    val system = ActorSystem("PrinterSystem")
    val printer = system.actorOf(MyFirstActor.props, "printer")
    printer ! "Hello!"
    printer ! 445
  }

  def MinimalTask2(): Unit = {
    val system = ActorSystem("PrinterModifierSystem")
    val printerModifier = system.actorOf(AdjustActor.props, "printerModifier")
    printerModifier ! "Hello"
    printerModifier ! 5
    printerModifier ! List(1, 3)
  }

  def MinimalTask3(): Unit = {
    val system = ActorSystem("MonitoringActorsSystem")
    val secondActor = system.actorOf(SecondActor.props, "secondActor")
    system.actorOf(FirstActor.props(secondActor), "firstActor")
    secondActor ! "start"
    secondActor ! "stop"

  }

  def MinimalTask4(): Unit = {
    val system = ActorSystem("AverageComputerSystem")
    val averageComputer = system.actorOf(ComputeAverageActor.props, "averageComputer")
    averageComputer ! 0
    averageComputer ! 10
    averageComputer ! 10
    averageComputer ! 10
  }

  def MainTask1(): Unit ={
    val Pid = new_queue()
    push(Pid, 4)
    push(Pid, 5)
    pop(Pid)
    pop(Pid)
  }

  def new_queue(): ActorRef={
    val system = ActorSystem("NewQueueActor")
    return system.actorOf(QueueActor.props, "queueActor")
  }

  def push(actor: ActorRef, number : Int): Unit ={
    actor ! number
  }

  def pop(actor: ActorRef): Unit ={
    actor ! "push"
  }
}

