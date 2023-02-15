import org.scalatest.funsuite.AnyFunSuite

  class HelloWorldTest extends AnyFunSuite {
    test("HelloPTR.hello returns the correct string") {
      assert(FirstLab.Week1.Main.hello() == "Hello, PTR!")
    }
  }


