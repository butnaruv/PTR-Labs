package FirstLab.Week2.Minimal

import scala.math.sqrt

//Task1 -> Test if a number is prime
object Task1 extends App {
  {
    println(checkPrimeNumber(13))
  }

  def checkPrimeNumber(number: Int): Boolean = {
    var isPrime = true
    if (number <= 1) {
      isPrime = false
    } else {
      for (i <- 2 to sqrt(number).toInt) {
        if (number % i == 0) isPrime = false;
      }
    }
    return isPrime
  }
}
