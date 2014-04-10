/* TP 7, 8 et 9 : calcul de l'ensemble de Mandelbrot,   */
/*             concurrence : Futures et Actors          */
/* Univ. Lille 1 - M1 Info - CALP                       */
/* v 2.0 - 2013-04-05                                   */
/* Licence Creative Commons BY SA                       */
/* @author Pierre Boulet <Pierre.Boulet@univ-lille1.fr> */

package calp.util

case class Complex(re: Double, im: Double) {
  // represents the complex number a + b*i
  def +(that: Complex) = Complex(this.re + that.re, this.im + that.im)
  def -(that: Complex) = Complex(this.re - that.re, this.im - that.im)
  def *(that: Complex) = Complex(this.re * that.re - this.im * that.im, this.re * that.im + that.re * this.im)
  def /(that: Complex) = {
    val d = that.sqAbs
    Complex((this.re * that.re - this.im * that.im)/d, (this.im * that.re - this.re * that.im)/d)
  }
  def scale(s: Double) = Complex(re*s, im*s)
  def sqAbs = re * re + im * im
  def abs = math.sqrt(sqAbs)
  def box(s: Double) = {
    val h = Complex(s/2, s/2)
    (this-h, this+h)
  }
}

object Complex {
  val i = new Complex(0, 1)
  implicit def double2Complex(real: Double) = Complex(real, 0)
}

