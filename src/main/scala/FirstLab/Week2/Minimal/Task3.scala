package FirstLab.Week2.Minimal

//Task 3 -> reverse a list
object Task3 extends App {
  {
    val myList: Array[Int] = Array(1, 2, 4, 8, 4)
    reverseList(myList: _*)
  }

  def reverseList(myList: Int*): Unit = Array[Int] {
    var reversedList = new Array[Int](myList.length)
    var s = myList.length - 1
    for (i <- 0 until myList.length) {
      reversedList(s) = myList(i)
      s = s - 1
    }
    return for (i <- reversedList) {
      print(i)
    }
  }

}
