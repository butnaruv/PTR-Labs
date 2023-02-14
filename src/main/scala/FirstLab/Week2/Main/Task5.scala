object Task5 extends App {
  {
    groupAnagrams(Array("eat", "tea", "tan", "ate", "nat", "bat"))
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