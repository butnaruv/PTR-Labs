object Task7 extends App {
  {
    val dictionary = Map("mama" -> "mother",
      "papa" -> "father")
    translator(dictionary, "mama is with papa")
  }

  def translator(dictionary: Map[String, String], input: String): Unit = {
    var output = input

    dictionary.keys.foreach(key => {
      if (input.contains(key)) {
        output = output.replace(key, dictionary(key))
      }
    })

    println(output)
  }
}