/* TP 7, 8 et 9 : calcul de l'ensemble de Mandelbrot,   */
/*             concurrence : Futures et Actors          */
/* Univ. Lille 1 - M1 Info - CALP                       */
/* v 1.0 - 2013-03-25                                   */
/* Licence Creative Commons BY SA                       */
/* @author Pierre Boulet <Pierre.Boulet@univ-lille1.fr> */

package calp.util

// RBG color manipulation
object rgb {
  def apply(red: Int, green: Int, blue: Int) = ((((red & 255) << 8) + (green & 255)) << 8) + (blue & 255)
  def unapply(c: Int) = Some(c >> 16, c >> 8 & 255, c & 255)
  def gray(level: Int) = apply(level, level, level)
  def average(ps: Seq[Int]) =
    rgb((ps map { case rgb(r, g, b) => r }).sum / ps.length,
      (ps map { case rgb(r, g, b) => g }).sum / ps.length,
      (ps map { case rgb(r, g, b) => b }).sum / ps.length)
  def hsv(h: Int, s: Double, v: Double) = {
    // h is the hue in ° between 0 and 359
    // s is the saturation between 0. and 1.
    // v is the value between 0. and 1.
    val c = v * s
    val hh = (h % 360) / 60
    val x = c * (1 - (hh % 2 - 1).abs)
    val rgb1 =
      if (hh < 1) (c, x, 0.)
      else if (hh < 2) (x, c, 0.)
      else if (hh < 3) (0., c, x)
      else if (hh < 4) (0., x, c)
      else if (hh < 5) (x, 0., c)
      else if (hh < 6) (c, 0., x)
      else (0., 0., 0.)
    val m = v - c
    val toRGB = (x: Double) => (x+m)*255 toInt
    
    rgb(toRGB(rgb1._1), toRGB(rgb1._2), toRGB(rgb1._3))
  }
}