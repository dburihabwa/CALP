/* TP 7, 8 et 9 : calcul de l'ensemble de Mandelbrot,   */
/*             concurrence : Futures et Actors          */
/* Univ. Lille 1 - M1 Info - CALP                       */
/* v 2.2 - 2013-05-25                                   */
/* Licence Creative Commons BY SA                       */
/* @author Pierre Boulet <Pierre.Boulet@univ-lille1.fr> */

package calp

import calp.util._
import calp.mandel._
import calp.util.Complex._
import scala.concurrent._
import scala.concurrent.duration._
import ExecutionContext.Implicits.global

// Future based renderer
class Renderer(
    r: String, c: Complex,
    img: ImageBuffer,
    ip: ImagePanel,
    tileSize: Int = 100,
    var maxIter: Int = 1000,
    var palette: String) {
  def render(cs: CoordinateSystem) {
    val tiles = cs.tiles(tileSize)
    /* Uncomment for progressive rendering
    // computes the color of the base point of the tile for progressive rendering
    for {
      x <- 0 until tiles.length
      y <- 0 until tiles(0).length
      i0 = x * tileSize
      j0 = cs.height - ((y + 1) * tileSize)
    } yield {
      future {
        val basePoint = palette(r.point(tiles(x)(y).ll, maxIter))
        img.blit(i0, j0, Array.fill(tileSize, tileSize)(basePoint))
      }
    }
    ip.repaint */
    // computes the color of all the points of the tiles
    for {
      x <- 0 until tiles.length
      y <- 0 until tiles(0).length
      i0 = x * tileSize
      j0 = cs.height - ((y + 1) * tileSize)
    } yield {
      future {
        fractale(r, c)(cs).image(maxIter, palettes(palette), tiles(x)(y))
      } map {
        src =>
          {
            img.blit(i0, j0, src)
            ip.repaint
          }
      }
    }
  }
}