package FirstLab.Week2

//Task 9 ->
object Task9 extends App {
  {
    rotateLeft(Array(1, 2, 4, 8, 4), 3)
  }

  def rotateLeft(myList: Array[Int], n: Int): Unit = {
    val newList = new Array[Int](myList.length)
    var newIndex = 0
    for (i <- 0 until myList.length) {
      newIndex = ((i - n) + myList.length) % myList.length
      newList(newIndex) = myList(i)
    }
    for(i <- newList)
    print(i + " ")
  }
}