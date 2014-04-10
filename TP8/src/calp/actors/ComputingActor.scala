/* TP 7, 8 et 9 : calcul de l'ensemble de Mandelbrot,   */
/*             concurrence : Futures et Actors          */
/* Univ. Lille 1 - M1 Info - CALP                       */
/* v 2.2 - 2013-05-25                                   */
/* Licence Creative Commons BY SA                       */
/* @author Pierre Boulet <Pierre.Boulet@univ-lille1.fr> */

package calp.actors

import akka.actor.Actor
import calp.mandel._
import calp.util._
import calp.actors.messages._
import akka.actor.ActorRef

class ComputingActor(maxIter : Int) extends Actor {
	
	println("Starting ComputingActor " + self.path) // for debug
	
	def receive = {
		case ComputeMessage(x, y, cs, palette, f, refreshingActor) => {
			val p = palettes(palette)
			val pixels = fractale(f)(cs).image(maxIter, p, cs)
			refreshingActor ! RefreshMessage(x, y, pixels)
		}			
	}
}
