/* TP 7, 8 et 9 : calcul de l'ensemble de Mandelbrot,   */
/*             concurrence : Futures et Actors          */
/* Univ. Lille 1 - M1 Info - CALP                       */
/* v 2.0 - 2013-04-05                                   */
/* Licence Creative Commons BY SA                       */
/* @author Pierre Boulet <Pierre.Boulet@univ-lille1.fr> */

package calp.util

import swing._                                                                

import java.awt.image.BufferedImage                                           
import java.io.File                                                           
import javax.imageio.ImageIO                                                  

// a panel to display an image
class ImagePanel extends Panel                                                
{                                                                             
  private var _imagePath = ""                                                 
  private var bufferedImage: BufferedImage = null                              

  def imagePath = _imagePath                                                  

  def imagePath_=(value:String)                                               
  {                                                                           
    _imagePath = value                                                        
    bufferedImage = ImageIO.read(new File(_imagePath))                        
  }                                                                           

  def image = bufferedImage
  
  def image_=(bi: BufferedImage) {
    bufferedImage = bi
  }
  
  override def paintComponent(g:Graphics2D) =                                 
  {                                                                           
    if (null != bufferedImage) g.drawImage(bufferedImage, 0, 0, null)         
  }                                                                           
}                                                                             

object ImagePanel                                                             
{                                                                             
  def apply() = new ImagePanel()                                              
}
