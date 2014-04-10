/* TP 7, 8 et 9 : calcul de l'ensemble de Mandelbrot,   */
/*             concurrence : Futures et Actors          */
/* Univ. Lille 1 - M1 Info - CALP                       */
/* v 2.0 - 2013-04-05                                   */
/* Licence Creative Commons BY SA                       */
/* @author Pierre Boulet <Pierre.Boulet@univ-lille1.fr> */

package calp.actors

import akka.actor.Actor
import messages._
import calp.util.ImageBuffer
import calp.util.ImagePanel

class RefreshingActor(img: ImageBuffer, ip: ImagePanel) extends Actor {
	def receive = {
		???
	}
}
