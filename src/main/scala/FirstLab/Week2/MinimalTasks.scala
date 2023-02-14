import scala.collection.mutable.ArrayBuffer
import scala.math.{pow, sqrt}
import scala.util.Random

object MinimalTasks {
  def isPrime(number: Int): Unit = {
    var isPrime = true
    if (number <= 1) {
      isPrime = false
    } else {
      for (i <- 2 to sqrt(number).toInt) {
        if (number % i == 0) isPrime = false;
      }
    }
    println(isPrime)
  }

  def cylinderArea(height: Int, radius: Int): Unit = {
    @inline val Pi = java.lang.Math.PI
    val baseArea = Pi * pow(radius, 2)
    val lateralArea = 2 * Pi * radius * height;
    val cylinderArea = 2 * baseArea + lateralArea

    println((cylinderArea * 10000).round / 10000.toDouble)
  }

  def reverseList(myList: Array[Int]): Unit = {
    var reversedList = new Array[Int](myList.length)
    var s = myList.length - 1
    for (i <- 0 until myList.length) {
      reversedList(s) = myList(i)
      s = s - 1
    }
    return for (i <- reversedList) {
      print(i)
    }
    println()
  }

  def uniqueSum(myList: Array[Int]): Unit = {
    var sum = 0
    val newList = myList.to(collection.mutable.Set)
    for (s <- newList) sum = sum + s
    println(sum)
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
    println()
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
    println()
  }

  def translator(dictionary: Map[String, String], input: String): Unit = {
    var output = input

    dictionary.keys.foreach(key => {
      if (input.contains(key)) {
        output = output.replace(key, dictionary(key))
      }
    })

    println(output)
  }

  def smallestNumber(myList: ArrayBuffer[Int]): Unit = {
    var hasZero = false
    var numberOfZeros = 0
    var minimal = ""
    if (myList.contains(0)) {
      hasZero = true
      numberOfZeros = myList.count(_ == 0)

    }
    for (i <- 0 until myList.length) {
      val value = myList.min
      minimal = minimal + value
      myList -= value
    }
    if (hasZero) {
      minimal = minimal.updated(0, minimal.charAt(numberOfZeros))
      for (i <- 1 to numberOfZeros) {
        minimal = minimal.updated(i, '0')
      }
    }
    println(minimal)
  }

  def rotateLeft(myList: Array[Int], n: Int): Unit = {
    val newList = new Array[Int](myList.length)
    var newIndex = 0
    for (i <- 0 until myList.length) {
      newIndex = ((i - n) + myList.length) % myList.length
      newList(newIndex) = myList(i)
    }
    for (i <- newList) {
      print(i + " ")
    }
    println()
  }

  def listRightAngleTriangles(): Unit = {
    for (
      a <- 1 to 20 by 1;
      b <- a to 20 by 1
    ) {
      val c = math.sqrt(a * a + b * b).toInt
      if (a * a + b * b == c * c) {
        println(s"$a $b $c")
      }
    }
  }

}