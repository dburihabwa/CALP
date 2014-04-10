/* TP 7, 8 et 9 : calcul de l'ensemble de Mandelbrot,   */
/*             concurrence : Futures et Actors          */
/* Univ. Lille 1 - M1 Info - CALP                       */
/* v 1.0 - 2013-03-25                                   */
/* Licence Creative Commons BY SA                       */
/* @author Pierre Boulet <Pierre.Boulet@univ-lille1.fr> */

package calp.mandel

import calp.util.Complex

trait Rectangle {
  val mainCS: CoordinateSystem
  
  def point(c: Complex, maxIter: Int): Int

  def build(maxIter: Int, cs: CoordinateSystem = mainCS) =
    Array.tabulate(cs.width, cs.height)(
      (i: Int, j: Int) => point(cs.imageToComplex(i, j), maxIter))

  def mapToColor(iters: Array[Array[Int]], palette: Int => Int, cs: CoordinateSystem = mainCS) =
    Array.tabulate(cs.width, cs.height)((i: Int, j: Int) => palette(iters(i)(j)))
  
  def image(maxIter: Int, palette: Int => Int, cs: CoordinateSystem = mainCS) = 
    mapToColor(build(maxIter, cs), palette, cs)
}