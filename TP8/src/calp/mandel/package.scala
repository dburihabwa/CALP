/* TP 7, 8 et 9 : calcul de l'ensemble de Mandelbrot,   */
/*             concurrence : Futures et Actors          */
/* Univ. Lille 1 - M1 Info - CALP                       */
/* v 2.2 - 2013-05-25                                   */
/* Licence Creative Commons BY SA                       */
/* @author Pierre Boulet <Pierre.Boulet@univ-lille1.fr> */

package calp

import calp.util.Complex

package object mandel {
  def mandelbrot(cs: CoordinateSystem) = new Rectangle with Mandelbrot with AntiAliasing {
    val mainCS = cs
    val scaleFactor = 3
  }
  
  def mandelbrot3(cs: CoordinateSystem) = new Rectangle with Mandelbrot3 with AntiAliasing {
    val mainCS = cs
    val scaleFactor = 3
  }
  
  def julia(cs: CoordinateSystem, j: Complex) = new Rectangle with Julia with AntiAliasing {
    val mainCS = cs
    val c = j
    val scaleFactor = 3
  }
  
  def fractale(name: String, center: Complex = Complex(0.0,0.0)) = {
    val m = Map("Mandelbrot" -> mandelbrot _,
        "Mandelbrot3" -> mandelbrot3 _,
        "Julia" -> ((cs: CoordinateSystem) => julia(cs, center)))
    try m(name) catch {
      case _: Exception => mandelbrot _
    }
  }
}