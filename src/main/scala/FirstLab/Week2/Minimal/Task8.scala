import scala.collection.mutable.ArrayBuffer

object Task8 extends App {
  {
    val list = ArrayBuffer(4, 0, 1, 0, 0)
    smallestNumber(list)
  }

  def smallestNumber(myList: ArrayBuffer[Int]): Unit = {
    var hasZero = false
    var numberOfZeros = 0
    var minimal = ""
    if (myList.contains(0)) {
      hasZero = true
      numberOfZeros = myList.count(_ == 0)

    }
    for (i <- 0 until myList.length) {
      val value = myList.min
      minimal = minimal + value
      myList -= value
    }
    if (hasZero) {
      minimal = minimal.updated(0, minimal.charAt(numberOfZeros))
      for (i <- 1 to numberOfZeros) {
        minimal = minimal.updated(i, '0')
      }
    }
    print(minimal)
  }
}