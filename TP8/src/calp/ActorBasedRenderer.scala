/* TP 7, 8 et 9 : calcul de l'ensemble de Mandelbrot,   */
/*             concurrence : Futures et Actors          */
/* Univ. Lille 1 - M1 Info - CALP                       */
/* v 2.2 - 2013-05-25                                   */
/* Licence Creative Commons BY SA                       */
/* @author Pierre Boulet <Pierre.Boulet@univ-lille1.fr> */

package calp

import calp.util._
import calp.mandel._
import calp.actors._
import akka.actor._
import calp.actors.messages._

class ActorBasedRenderer(
	r: String, c: Complex,
	img: ImageBuffer,
	ip: ImagePanel,
	tileSize: Int = 100,
	maxIter: Int = 1000,
	palette: String,
	initCS: CoordinateSystem)
		extends Renderer(r, c, img, ip, tileSize, maxIter, palette) {

	override def render(cs: CoordinateSystem) {
		???
	}
}
