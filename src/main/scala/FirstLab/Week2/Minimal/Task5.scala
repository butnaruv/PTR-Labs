package FirstLab.Week2.Minimal

import scala.util.Random

//Task 5 -> Extract a given number of randomly selected elements
object Task5 extends App {
  {
    extractRandomN(Array(1, 2, 4, 8, 4), 3)
  }

  def extractRandomN(myList: Array[Int], n: Int): Unit = {
    val rand = new Random()
    val randomList = new Array[Int](n)
    var s = 0
    for (i <- 0 until n) {
      s = rand.nextInt(myList.length - 1);
      while (randomList.contains(s)) {
        s = rand.nextInt(myList.length - 1);
      }
      randomList(i) = s
      print(myList(s) + " ")
    }
  }
}
