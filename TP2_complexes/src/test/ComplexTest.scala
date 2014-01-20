package test

import org.scalatest._
import calp.util.Complex

class ComplexTest extends FlatSpec with Matchers {
  "Complex" should "be easy to work with" in {
    Complex(1, 0) should be { new Complex(1, 0) }
    Complex(1, 0).re should be(1)
    Complex(1, 0).im should be(0)
    Complex(4, 2) == Complex(4, 2) should be(true)
    Complex(4, 2).toString should be("Complex(4.0,2.0)")
    (Complex(4, 2) match {
      case Complex(r, i) => 10 * r + i
    }) should be(42.0)
    //test +
    Complex(4, 2) + Complex(1, 1) should be { new Complex(5, 3) }
    Complex(4, 2) - Complex(1, 1) should be { new Complex(3, 1) }
    Complex(4, 2) * Complex(1, 1) should be { new Complex(2, 6) }
  }
}