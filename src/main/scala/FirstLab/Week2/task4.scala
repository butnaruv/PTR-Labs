import scala.collection.mutable
import scala.collection.mutable.Buffer
object task4 extends App{
  {
    print(letterCombinations("23"))
  }
    def letterCombinations(digits: String): List[String] = {
      if (digits == null || digits.isEmpty) {
        return List()
      }
      backtrack(List(), new StringBuilder(), digits, 0)
    }

    def backtrack(combinations: List[String], current: StringBuilder, digits: String, index: Int): List[String] = {
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
        return combinations :+ current.toString
      }
      val digit = digits(index)
      val letters = phoneMap(digit)
      var newCombinations = Buffer[String]()
      letters.foreach { letter =>
        current.append(letter)
        newCombinations ++= backtrack(combinations, current, digits, index + 1)
        current.deleteCharAt(current.length - 1)
      }
      combinations ++ newCombinations.toList
    }
}