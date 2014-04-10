/* TP 7, 8 et 9 : calcul de l'ensemble de Mandelbrot,   */
/*             concurrence : Futures et Actors          */
/* Univ. Lille 1 - M1 Info - CALP                       */
/* v 2.2 - 2013-05-25                                   */
/* Licence Creative Commons BY SA                       */
/* @author Pierre Boulet <Pierre.Boulet@univ-lille1.fr> */

package calp.util
import scala.math._

// the palettes convert a number of iterations to a color
object palettes {
  def grayscale(iter: Int): Int = rgb.gray(iter)
  def color(iter: Int): Int = {
    if (iter == 0) rgb(0,0,0)
    else rgb(iter * (100-iter) / 15, 5 * iter / 2,  130 - iter)
  }
  def color2(iter: Int): Int = {
    if (iter == 0) rgb(0,0,0)
    else rgb.hsv(iter*2, sin(iter.toDouble/17.)/3.+0.6, sin(iter.toDouble/3.)/4.+0.7)
  }
  def apply(name: String): Int => Int = {
    val paletteMap = Map("grayscale" -> grayscale _,
        "color" -> color _,
        "color2" -> color2 _)
    try paletteMap(name) catch {
      case _: Exception => color
    }
  }
}