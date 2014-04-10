/* TP 7, 8 et 9 : calcul de l'ensemble de Mandelbrot,   */
/*             concurrence : Futures et Actors          */
/* Univ. Lille 1 - M1 Info - CALP                       */
/* v 1.0 - 2013-03-25                                   */
/* Licence Creative Commons BY SA                       */
/* @author Pierre Boulet <Pierre.Boulet@univ-lille1.fr> */

package calp.mandel

import scala.concurrent._
import scala.concurrent.duration._
import ExecutionContext.Implicits.global

trait FutureTiling extends Rectangle with Tiling {
  def fimage(maxIter: Int, palette: Int => Int, cs: CoordinateSystem = mainCS) = {
    val res: Array[Array[Int]] = Array.ofDim(cs.width, cs.height)
    val tiles = cs.tiles(tileSize)
    val fs = for {
      x <- 0 until tiles.length
      y <- 0 until tiles(0).length
      i0 = x * tileSize
      j0 = cs.height - ((y+1) * tileSize)
    } yield {future {super.image(maxIter, palette, tiles(x)(y))} map (src => blit(i0, j0, src, res))}
    future {
      fs foreach (f => Await.ready(f, Duration.Inf))
      res
      }
  }
  
  override def image(maxIter: Int, palette: Int => Int, cs: CoordinateSystem = mainCS) =
    Await.result(fimage(maxIter, palette, cs), Duration.Inf)

}