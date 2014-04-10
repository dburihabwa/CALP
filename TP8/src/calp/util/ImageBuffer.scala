/* TP 7, 8 et 9 : calcul de l'ensemble de Mandelbrot,   */
/*             concurrence : Futures et Actors          */
/* Univ. Lille 1 - M1 Info - CALP                       */
/* v 2.0 - 2013-04-05                                   */
/* Licence Creative Commons BY SA                       */
/* @author Pierre Boulet <Pierre.Boulet@univ-lille1.fr> */

package calp.util

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class ImageBuffer(width: Int, height: Int) {
  // the bitmap as from awt
  val buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)

  // sets the color of a single pixel of coordinates (i,j)
  def setPixel(i: Int, j: Int, rgbcolor: Int) {
    buffer.setRGB(i, j, rgbcolor)
  }

  // fills the bitmap with values from an array of the correct size
  def fill(pixels: Array[Array[Int]]) {
    for (i <- 0 until width; j <- 0 until height) setPixel(i, j, pixels(i)(j))
  }

  // fills a rectangular region of the image of upper left corner 
  // coordinates (i0,j0) and of values stored in the pixels Array
  def blit(i0: Int, j0: Int, pixels: Array[Array[Int]]) {
    for {
      i <- 0 until pixels.length
      j <- 0 until pixels(0).length
      i1 = i0 + i if i1 < width && i1 >= 0
      j1 = j0 + j if j1 < height && j1 >= 0
    } setPixel(i1, j1, pixels(i)(j))

  }

  // writes the image in file filename
  def write(filename: String) {
    val outputfile = new File(filename)
    ImageIO.write(buffer, "png", outputfile)
  }
}
