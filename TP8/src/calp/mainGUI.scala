/* TP 7, 8 et 9 : calcul de l'ensemble de Mandelbrot,   */
/*             concurrence : Futures et Actors          */
/* Univ. Lille 1 - M1 Info - CALP                       */
/* v 2.2 - 2013-05-25                                   */
/* Licence Creative Commons BY SA                       */
/* @author Pierre Boulet <Pierre.Boulet@univ-lille1.fr> */

package calp

import scala.swing._
import scala.swing.event._

import scala.concurrent._
import scala.concurrent.duration._
import ExecutionContext.Implicits.global

import calp.util._
import calp.util.Complex._
import calp.mandel.{Rectangle => Fractale, _}

object mainGUI extends SimpleSwingApplication {
  // Parameters
  val width = 800
  val height = 800
  val maxIter = 1000
  val center = Complex(0, 0) //used only for Julia, the value has no effet on Mandelbrot fractals
  val initCS = CoordinateSystem.centered(width, height, Complex(-1, 0), 3)
  val fractal = "Mandelbrot"
  //val initCS = CoordinateSystem.centered(width, height, Complex(0, 0), 3)
  //val fractal = "Mandelbrot3"
  //val initCS = CoordinateSystem.centered(width, height, Complex(0, 0), 4)
  //val fractal = "Julia"
  //val center = 0.285+0.01*i
  //val center = 0.3+0.5*i
  //val center = -1.417022285618 + 0.0099534*i
  //val center = -0.038088 + 0.9754633*i
  //val center = 0.285 + 0.013*i
  
  val palette = "color2"
  val tileSize = 25
  val scalingFactor = Map(0 -> 0.33, 256 -> 2.0)
  // main window
  def top = new MainFrame {
    title = "Mandelbrot Explorer"
    preferredSize = new Dimension(width + 40, height + 40)
    contents = new ScrollPane {
      contents = new ImagePanel {
        preferredSize = new Dimension(width, height)
        val img = new calp.util.ImageBuffer(width, height)
        image = img.buffer
        var currentCS = initCS
        // initializing the renderer
       // val r1 = new Renderer(fractal, center, img, this, tileSize, maxIter, palette);
       // val r2 = new ActorBasedRenderer(fractal, center, img, this, tileSize, maxIter, palette,currentCS);
        val r = new ActorRouterRenderer(fractal, center, img, this, tileSize, maxIter, palette)
        // first rendering with the initial parameters
        r.render(currentCS)
        // zooming with the mouse clicks
        listenTo(this.mouse.clicks)
        reactions += {
          case MouseClicked(src, point, modifiers, clicks, b) => {
            currentCS = 
              (currentCS + 
                (currentCS.imageToComplex(point.x, point.y) - currentCS.center)
                ) * (scalingFactor(modifiers) * clicks)
            r.render(currentCS)
          }
        }
      }
    }
  }
}