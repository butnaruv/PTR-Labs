object BonusTask3 extends App {
  {
    toRoman(13)
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
    print(result)
  }
}