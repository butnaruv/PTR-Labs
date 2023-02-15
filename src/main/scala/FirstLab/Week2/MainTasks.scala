import scala.collection.mutable

object MainTasks {
  def removeConsecutiveDuplicates(myList: Array[Int]): Unit = {
    print(myList(0) + " ")
    for (i <- 1 until myList.length) {
      if (myList(i) != myList(i - 1)) {
        print(myList(i) + " ")
      }
    }
    println()
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
    println()
  }

  def encode(input: String, key: Int): Unit = {
    for (e <- input) {
      print(((e.toInt + key - 97) % 26 + 97).toChar)
    }
    println()
  }

  def decode(input: String, key: Int): Unit = {
    for (e <- input) {
      print(((e.toInt - key - 97) % 26 + 97).toChar)
    }
    println()
  }

  def letterCombinations(digits: String): List[String] = {
    if (digits == null || digits.isEmpty) {
      return List()
    }
    backtrack(List(), new StringBuilder(), digits, 0)
  }

  def backtrack(result: List[String], current: StringBuilder, digits: String, index: Int): List[String] = {
    val phoneMap = Map(
      '2' -> "abc",
      '3' -> "def",
      '4' -> "ghi",
      '5' -> "jkl",
      '6' -> "mno",
      '7' -> "pqrs",
      '8' -> "tuv",
      '9' -> "wxyz"
    )
    if (index == digits.length) {
      return result :+ current.toString
    }
    val digit = digits(index)
    val letters = phoneMap(digit)
    var newCombinations = mutable.Buffer[String]()
    letters.foreach { letter =>
      current.append(letter)
      newCombinations ++= backtrack(result, current, digits, index + 1)
      current.deleteCharAt(current.length - 1)
    }
    result ++ newCombinations.toList
  }

  def groupAnagrams(list: Array[String]): Unit = {
    var map: Map[String, Array[String]] = Map.empty[String, Array[String]]

    for (i <- 0 until list.length) {
      var temp = Array[String]()
      for (j <- 0 until list.length) {
        if (list(i).sorted == list(j).sorted) {
          temp = temp ++ Array(list(j))
        }
      }
      map += (temp(0).sorted -> temp)
    }
    map.foreach { case (key, values) =>
      println(s"$key -> ${values.mkString(", ")}")
    }
  }

}
