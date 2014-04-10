/* TP 7, 8 et 9 : calcul de l'ensemble de Mandelbrot,   */
/*             concurrence : Futures et Actors          */
/* Univ. Lille 1 - M1 Info - CALP                       */
/* v 2.0 - 2013-04-05                                   */
/* Licence Creative Commons BY SA                       */
/* @author Pierre Boulet <Pierre.Boulet@univ-lille1.fr> */

package calp

import calp.util._
import calp.util.Complex._
import calp.mandel._

object main extends App {
  val width = 1000
  val height = 1000
  val maxIter = 5000
  val dir = "/home/boulet/Bureau/fractales/"
  
  def render(r: Rectangle, file: String) = {
    println("Starting image "+file)
    val t0 = System.currentTimeMillis()
    val pixels = r.image(maxIter, palettes.color)
    val d = System.currentTimeMillis() - t0
    val img = new calp.util.ImageBuffer(width, height)
    println("Computed in "+d+" ms")
    img.fill(pixels)
    println("Filled")
    img.write(dir+file)
    println("Saved")
  }
  
  val mandel_full = new Rectangle with Mandelbrot with AntiAliasing with FutureTiling {
    val mainCS = CoordinateSystem(width, height, Complex(-2.2, -1.5), Complex(0.8, 1.5))
    val tileSize = 100
    val scaleFactor = 3
  }

  val mj2 = new Rectangle with Mandelbrot with AntiAliasing with FutureTiling {
    val c = -1.76866786283749 + 0.00164558054682 * i
    val b = c.box(0.000000008)
    val mainCS = CoordinateSystem(width, height, b._1, b._2)
    val tileSize = 100
    val scaleFactor = 3
  }

  val mandel3_full = new Rectangle with Mandelbrot3 with AntiAliasing with FutureTiling {
    val mainCS = CoordinateSystem(width, height, Complex(-1.5, -1.5), Complex(1.5, 1.5))
    val tileSize = 100
    val scaleFactor = 3
  }

  val julia1 = new Rectangle with Julia with AntiAliasing with FutureTiling {
    val c = 0.285+0.01*i 
    val mainCS = CoordinateSystem(width, height, Complex(-2, -2), Complex(2, 2))
    val tileSize = 100
    val scaleFactor = 3
  }

  val julia2 = new Rectangle with Julia with AntiAliasing with FutureTiling {
    val c = -1.417022285618 + 0.0099534*i
    val mainCS = CoordinateSystem(width, height, Complex(-2, -2), Complex(2, 2))
    val tileSize = 100
    val scaleFactor = 3
  }

  render(mandel_full, "mandel.png")
  render(mandel3_full, "mandel3.png")
  render(mj2, "mj2.png")
  render(julia1, "julia1.png")
  render(julia2, "julia2.png")
}