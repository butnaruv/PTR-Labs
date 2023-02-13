object Task2 extends App {
  {
    lineWords(List("Hello", "Alaska", "Dad", "Peace"))
  }

  def lineWords(list: List[String]): Unit = {
    val map = Map(
      0 -> List('q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p'),
      1 -> List('a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l'),
      2 -> List('z', 'x', 'c', 'v', 'b', 'n', 'm')
    )

    for (word <- list) {
      for (i <- 0 until map.size) {
        if (word.forall(c => map(i).contains(c.toLower))) print(word + " ")
      }
    }
  }
}
