object BonusTask2 extends App {
  {
    commonPrefix(Array("alpha", "beta", "gama"))
    commonPrefix(Array("flower", "flow", "flight"))

  }
  def commonPrefix(myList : Array[String]): Unit ={
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
}