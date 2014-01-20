package calp.util

case class Complex(re: Double, im: Double) {

  def sqAbs = re * re + im * im
  def +(c: Complex): Complex = {
    new Complex(this.re + c.re, this.im + c.im)
  }

  def -(c: Complex): Complex = {
    new Complex(this.re - c.re, this.im - c.im)
  }

  def *(c: Complex): Complex = {
    new Complex(this.re * c.re - this.im * c.im, this.im * c.re + this.re * c.im)
  }

  def /(c: Complex): Complex = {
    new Complex(this.re * c.re - this.im * c.im / c.sqAbs, this.im * c.re - this.re * c.im / c.sqAbs)
  }

  def scale(s: Double) = Complex(re * s, im * s)
}

object Complex {

  implicit def doubleToComplex(r: Double) = Complex(r, 0)
  val i = Complex(0,1)
}