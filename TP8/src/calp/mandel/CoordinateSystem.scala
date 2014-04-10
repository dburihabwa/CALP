/* TP 7, 8 et 9 : calcul de l'ensemble de Mandelbrot,   */
/*             concurrence : Futures et Actors          */
/* Univ. Lille 1 - M1 Info - CALP                       */
/* v 2.0 - 2013-04-05                                   */
/* Licence Creative Commons BY SA                       */
/* @author Pierre Boulet <Pierre.Boulet@univ-lille1.fr> */

package calp.mandel

import calp.util.Complex

case class CoordinateSystem (width: Int, height: Int, ll: Complex, ur: Complex) {
  val d = ur - ll
  val center = ll + d/2
  
  def imageToComplex(i: Int, j: Int) =
    ll + Complex(i.toDouble / width * d.re, (height - j).toDouble / height * d.im)
    
  def zoomBy(factor: Int) = CoordinateSystem(width*factor, height*factor, ll, ur)
  
  def +(s: Complex) = CoordinateSystem(width, height, ll+s, ur+s)
  
  def *(factor: Double) = CoordinateSystem.centered(width, height, center, (d.re)*factor)
  
  def tiles(tileSize: Int) = {
    val nbTilesHor = width / tileSize + (if (width % tileSize == 0) 0 else 1)
    val nbTilesVer = height / tileSize + (if (height % tileSize == 0) 0 else 1)
    val tileShape = Complex(d.re/nbTilesHor, d.im/nbTilesVer)
    
    Array.tabulate(nbTilesHor,nbTilesVer)((i,j) => 
      CoordinateSystem(
          tileSize,
          tileSize,
          ll+Complex(tileShape.re*i,tileShape.im*j),
          ll+Complex(tileShape.re*(i+1),tileShape.im*(j+1))
          ))
  }
}

object CoordinateSystem {
  def centered(width: Int, height: Int, c: Complex, d: Double) = {
    val r = Complex(d/2, d/2*height/width)
    CoordinateSystem(width, height, c-r, c+r)
  }
}