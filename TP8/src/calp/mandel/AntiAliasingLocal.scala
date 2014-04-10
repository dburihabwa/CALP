/* TP 7, 8 et 9 : calcul de l'ensemble de Mandelbrot,   */
/*             concurrence : Futures et Actors          */
/* Univ. Lille 1 - M1 Info - CALP                       */
/* v 2.1 - 2014-03-24                                   */
/* Licence Creative Commons BY SA                       */
/* @author Pierre Boulet <Pierre.Boulet@univ-lille1.fr> */

package calp.mandel

import calp.util.Complex
import calp.util.rgb

trait AntiAliasingLocal extends Rectangle {
  val scaleFactor: Int

  override def image(maxIter: Int, palette: Int => Int, cs: CoordinateSystem = mainCS) =
    Array.tabulate(cs.width, cs.height)((i: Int, j: Int) =>
      rgb.average(
        (super.image(maxIter, palette,
          new CoordinateSystem(
            scaleFactor,
            scaleFactor,
            cs.imageToComplex(i, j),
            cs.imageToComplex(i + 1, j + 1)))).flatten))
}