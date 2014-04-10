/* TP 7, 8 et 9 : calcul de l'ensemble de Mandelbrot,   */
/*             concurrence : Futures et Actors          */
/* Univ. Lille 1 - M1 Info - CALP                       */
/* v 1.0 - 2013-03-25                                   */
/* Licence Creative Commons BY SA                       */
/* @author Pierre Boulet <Pierre.Boulet@univ-lille1.fr> */

package calp.mandel

import calp.util.rgb

trait Tiling extends Rectangle {
  val tileSize: Int
  
  def blit(i0: Int, j0: Int, src: Array[Array[Int]], dest: Array[Array[Int]]) {
    for {
      i <- 0 until src.length
      j <- 0 until src(0).length
      i1 = i0 + i if i1 < dest.length
      j1 = j0 + j if j1 < dest(0).length
    }
      dest(i1)(j1) = src(i)(j)
  }

  override def image(maxIter: Int, palette: Int => Int, cs: CoordinateSystem = mainCS) = {
    val res: Array[Array[Int]] = Array.fill(cs.width, cs.height)(rgb.gray(255))
    val tiles = cs.tiles(tileSize)
    for {
      x <- 0 until tiles.length
      y <- 0 until tiles(0).length
      src = super.image(maxIter, palette, tiles(x)(y))
      i0 = x * tileSize
      j0 = cs.height - ((y+1) * tileSize)
    } blit(i0, j0, src, res)
    res
  }
}