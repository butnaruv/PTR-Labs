package FirstLab.Week2

import scala.math.pow

//Task2 -> Compute are of a cylinder, given the height and the radius.
object Task2 extends App {
  {
    print(cylinderArea(3, 4))
  }

  def cylinderArea(height: Int, radius: Int): Double = {
    @inline val Pi = java.lang.Math.PI
    val baseArea = Pi * pow(radius, 2)
    val lateralArea = 2 * Pi * radius * height;
    val cylinderArea = 2 * baseArea + lateralArea

    return ((cylinderArea * 10000).round / 10000.toDouble)
  }
}

