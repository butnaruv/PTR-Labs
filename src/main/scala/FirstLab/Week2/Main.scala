import scala.collection.mutable.{ArrayBuffer, ArrayStack}

object Main extends App {
  println("Minimal:")
  minimalTasks()
  println()

  println("Main:")
  mainTasks()
  println()

  println("Bonus:")
  bonusTasks()
  println()

  def minimalTasks() = {
    MinimalTasks.isPrime(5)

    MinimalTasks.cylinderArea(3, 4)

    MinimalTasks.reverseList(Array(1, 2, 4, 8, 4))

    MinimalTasks.uniqueSum(Array(1, 2, 4, 8, 4, 2))

    MinimalTasks.extractRandomN(Array(1, 2, 4, 8, 4), 3)

    MinimalTasks.firstFibonacciNumbers(7)

    MinimalTasks.translator(Map("mama" -> "mother", "papa" -> "father"), "mama is with papa")

    MinimalTasks.smallestNumber(ArrayBuffer(4, 5, 3))
    MinimalTasks.smallestNumber(ArrayBuffer(0, 3, 4))

    MinimalTasks.rotateLeft(Array(1, 2, 4, 8, 4), 3)

    MinimalTasks.listRightAngleTriangles()
  }

  def mainTasks() = {
    MainTasks.removeConsecutiveDuplicates(Array(1, 2, 2, 2, 4, 8, 4))

    MainTasks.lineWords(List("Hello", "Alaska", "Dad", "Peace"))

    MainTasks.encode("lorem", 3)
    MainTasks.decode("oruhp", 3)

    MainTasks.letterCombinations("23")

    MainTasks.groupAnagrams(Array("eat", "tea", "tan", "ate", "nat", "bat"))
  }

  def bonusTasks() = {
    BonusTasks.commonPrefix(Array("flower", "flow", "flight"))
    BonusTasks.commonPrefix(Array("alpha", "beta", "gamma"))

    BonusTasks.toRoman(999)

    BonusTasks.factorize(13)
    BonusTasks.factorize(42)
  }
}