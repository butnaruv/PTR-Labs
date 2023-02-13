object BonusTask1 extends App{
  {
    factorize(42)
  }
  def factorize(number : Int): Unit ={
    var list = Array[Int]()
    var n = number
    for (i <- 2 to n) {
      while (n % i == 0) {
        list = list :+ i
        n = n / i
      }
    }
    for (e <- list) print(e + " ")
  }

}

//List<Integer> primeFactors = new ArrayList<>();
//for (int i = 2; i <= n / i; i++) {
//  while (n % i == 0) {
//    primeFactors.add(i);
//    n = n / i;
//  }
//}
//if (n > 1) {
//  primeFactors.add(n);
//}
//return primeFactors;
//}