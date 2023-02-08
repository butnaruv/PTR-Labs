package FirstLab.Week2

//Task 4 -> Compute sum of unique elements in a list
object Task4 extends App {
  {
    val myList: Array[Int] = Array(1, 2, 4, 8, 4)
    sumOfAList(myList: _*)
  }

  def sumOfAList(myList: Int*): Unit = {
    var sum = 0
    val newList = myList.to(collection.mutable.Set)
    for (s <- newList) sum = sum + s
    print(sum)
  }

}