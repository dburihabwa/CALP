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
import calp.actors.ComputingActor



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
		val tiles = cs.tiles(tileSize)
		val system = ActorSystem.create("renderer")
		val refreshingActor = system.actorOf(Props(new RefreshingActor(img, ip)), name = "refreshingActor")
		val compActs = 
    Array.tabulate(tiles.length,tiles(0).length)(
        (i: Int, j: Int) => system.actorOf(Props(new ComputingActor(maxIter)), name = "comp"+i+"-"+j))
		for {
			x <- 0 until tiles.length
			y <- 0 until tiles(0).length
			i0 = x * tileSize
			j0 = cs.height - ((y + 1) * tileSize)
		} {
			compActs(x)(y) ! ComputingMessage(i0, j0, tiles(x)(y), palette, r, refreshingActor)
		}
	}
}
