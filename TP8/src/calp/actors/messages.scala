/* TP 7, 8 et 9 : calcul de l'ensemble de Mandelbrot,   */
/*             concurrence : Futures et Actors          */
/* Univ. Lille 1 - M1 Info - CALP                       */
/* v 2.2 - 2013-05-25                                   */
/* Licence Creative Commons BY SA                       */
/* @author Pierre Boulet <Pierre.Boulet@univ-lille1.fr> */

package calp.actors

import calp.mandel.CoordinateSystem
import akka.actor.ActorRef
import calp.mandel.Rectangle
import calp.util.Complex

object messages {
	case class ComputingMessage(x:Int, y: Int, cs: CoordinateSystem, palette: String, fractale: String, refreshingActor: ActorRef)
	case class RefreshingMessage(x:Int, y:Int, pixels: Array[Array[Int]])
}
