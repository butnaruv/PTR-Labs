object Task3 extends App {
  {
    encode("lorem", 3)
    decode("oruhp", 3)
  }
  def encode(input: String, key: Int): Unit ={

    for(e <- input){
      print((e.toInt + key).toChar)
    }
    println()
  }
  def decode(input: String, key: Int): Unit ={
    for (e <- input) {
      print((e.toInt - key).toChar)
    }
    println()
  }
}