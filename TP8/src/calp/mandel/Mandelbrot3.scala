/* TP 7, 8 et 9 : calcul de l'ensemble de Mandelbrot,   */
/*             concurrence : Futures et Actors          */
/* Univ. Lille 1 - M1 Info - CALP                       */
/* v 1.0 - 2013-03-25                                   */
/* Licence Creative Commons BY SA                       */
/* @author Pierre Boulet <Pierre.Boulet@univ-lille1.fr> */

package calp.mandel

import calp.util.Complex
import calp.util.Complex._

trait Mandelbrot3 {
  def point(c: Complex, maxIter: Int) = {
    var z = 0.0 + i * 0.0
    var step = 0

    while (z.sqAbs < 4 && step < maxIter) {
      step += 1
      z = z * z *z + c
    }
    if (step == maxIter) 0 else step
  }
}