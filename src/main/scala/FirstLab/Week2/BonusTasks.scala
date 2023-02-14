object BonusTasks {
  def commonPrefix(myList: Array[String]): Unit = {
    var common = ""
    var ready = true
    var i = 0
    while (ready) {
      common = common + myList(0)(i)
      if (myList.forall(word => word.startsWith(common))) {
        i = i + 1
      } else {
        ready = false
        common = common.dropRight(1)
      }
    }
    println(common)
  }

  def toRoman(input: Int): Unit = {
    val tuples = Map(
      1000 -> "M",
      900 -> "CM",
      500 -> "D",
      400 -> "CD",
      100 -> "C",
      90 -> "XC",
      50 -> "L",
      40 -> "XL",
      10 -> "X",
      9 -> "IX",
      5 -> "V",
      4 -> "IV",
      1 -> "I"
    )
    var result = ""
    var num = input
    val sortedKeys = tuples.keys.toList.sorted.reverse
    for (x <- sortedKeys) {
      while (num >= x) {
        result += tuples(x)
        num = num - x
      }
    }
    println(result)
  }

  def factorize(number: Int): Unit = {
    var list = Array[Int]()
    var n = number
    for (i <- 2 to n) {
      while (n % i == 0) {
        list = list :+ i
        n = n / i
      }
    }
    for (e <- list) print(e + " ")
  }
  println()
}