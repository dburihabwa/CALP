/* TP 7, 8 et 9 : calcul de l'ensemble de Mandelbrot,   */
/*             concurrence : Futures et Actors          */
/* Univ. Lille 1 - M1 Info - CALP                       */
/* v 1.0 - 2013-03-25                                   */
/* Licence Creative Commons BY SA                       */
/* @author Pierre Boulet <Pierre.Boulet@univ-lille1.fr> */

package calp.mandel

import calp.util.Complex
import calp.util.rgb

trait AntiAliasing extends Rectangle {
  val scaleFactor: Int

  def antiAliasImage(scaledImage: Array[Array[Int]], cs: CoordinateSystem = mainCS) =
    Array.tabulate(cs.width, cs.height)(
      (x, y) => rgb.average(for {
        i <- x * scaleFactor until (x + 1) * scaleFactor
        j <- y * scaleFactor until (y + 1) * scaleFactor
      } yield scaledImage(i)(j)))

  override def image(maxIter: Int, palette: Int => Int, cs: CoordinateSystem = mainCS) = 
    antiAliasImage(super.image(maxIter, palette, cs.zoomBy(scaleFactor)),cs)
}