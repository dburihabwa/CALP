/* TP 7, 8 et 9 : calcul de l'ensemble de Mandelbrot,   */
/*             concurrence : Futures et Actors          */
/* Univ. Lille 1 - M1 Info - CALP                       */
/* v 2.2 - 2013-05-25                                   */
/* Licence Creative Commons BY SA                       */
/* @author Pierre Boulet <Pierre.Boulet@univ-lille1.fr> */

package calp

import calp.mandel._
import calp.util._
import calp.actors._
import akka.actor._
import calp.actors.messages._
import akka.routing._
import com.typesafe.config.ConfigFactory

class ActorRouterRenderer(
    r: String, c: Complex,
    img: ImageBuffer,
    ip: ImagePanel,
    tileSize: Int = 1000,
    maxIter: Int = 1000,
    palette: String) extends Renderer(r, c, img, ip, tileSize, maxIter, palette) {

  override def render(cs: CoordinateSystem) {
    ???
  }
}
