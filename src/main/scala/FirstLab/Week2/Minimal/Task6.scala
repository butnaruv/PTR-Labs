package FirstLab.Week2.Minimal

//Task 6 -> Find first n fibonacci numbers
object Task6 extends App {
  {
    firstFibonacciNumbers(7)
  }

  def firstFibonacciNumbers(n: Int): Unit = {
    var fib1 = 1
    var fib2 = 1
    if (n == 1 || n == 0) print(n)
    else {
      print(fib1 + " ")
      for (i <- 2 to n) {
        print(fib2 + " ")
        val sumOfTwoPrecedent = fib1 + fib2
        fib1 = fib2
        fib2 = sumOfTwoPrecedent
      }
    }
  }
}
