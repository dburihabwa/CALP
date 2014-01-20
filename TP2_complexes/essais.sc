object essais {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  import test._
  import calp.util._
  import calp.util.Complex._
  (new ComplexTest).execute                       //> [32mComplexTest:[0m
                                                  //| [32mComplex[0m
                                                  //| [32m- should be easy to work with[0m
  2.im                                            //> res0: Double = 0.0
  2.re                                            //> res1: Double = 2.0
  Complex(2,4) + 6                                //> res2: calp.util.Complex = Complex(8.0,4.0)
  3+9*i                                           //> res3: calp.util.Complex = Complex(3.0,9.0)
}