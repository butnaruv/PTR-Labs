object Task10 extends App{
  {
    listRightAngleTriangles()
  }
  def listRightAngleTriangles(): Unit = {
    for (
      a <- 1 to 20 by 1;
      b <- a to 20 by 1
    ) {
      val c = math.sqrt(a * a + b * b).toInt
      if (a * a + b * b == c * c) {
        println(s"$a $b $c")
      }
    }
  }

}