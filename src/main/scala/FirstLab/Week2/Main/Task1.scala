object Task1 extends App{
  {
    removeConsecutiveDuplicates(Array(1 , 2 , 2 , 2 , 4 , 8 , 4))
  }
  def removeConsecutiveDuplicates(myList: Array[Int]): Unit ={
    print(myList(0) + " ")
    for(i <- 1 until myList.length){
      if(myList(i) != myList(i-1)){
        print(myList(i) + " ")
      }
    }
  }
}
