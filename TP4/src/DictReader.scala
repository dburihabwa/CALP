import java.io.InputStream
import scala.io.BufferedSource
import java.io.FileInputStream
import scala.io.BufferedSource
import java.text.Normalizer

class DictReader(filename: String = "/usr/share/hunspell/fr.dic") {
  val lines: List[String] = getLinesFromFile;
  val words: List[String] = getWordsFromLines;

  lazy val truePalindromes = getTruePalindromes;

  lazy val palindromes = getPalindromes;

  lazy val newPalindromes = getNewPalindromes;

  lazy val longestWord = getLongestWord;

  def getLinesFromFile(): List[String] = {
    val file = new BufferedSource(new FileInputStream(filename))
    file.getLines.toList.tail
  }

  def getWordsFromLines(): List[String] = {
    lines.map(line => line.split("/")(0))
  }

  def getTruePalindromes(): List[String] = {
    words.filter(word => word.equals(word.reverse))
  }
  def getPalindromes(): List[String] = {
    words.filter(word => {
      val str = Normalizer.normalize(word.toLowerCase(), Normalizer.Form.NFD)
      val exp = "\\p{InCombiningDiacriticalMarks}+".r
      val normalized = exp.replaceAllIn(str, "")
      normalized.equals(normalized.reverse)
    })
  }
  def getNewPalindromes(): List[String] = {
    palindromes.diff(truePalindromes)
  }

  def getLongestWord(): String = {
    var currentLongestWord = words.head
    def compareLongest(a: String, b: String): String = {
      if (a.length() < b.length())
        b
      else
        a
    }
    words.foldLeft(currentLongestWord)((word, xs) => {
      currentLongestWord = compareLongest(currentLongestWord, word)
      println(word +  " < " + currentLongestWord)
      currentLongestWord
    })
  }

}
object Hi extends App {
  override def main(args: Array[String]): Unit = {
    val dr = new DictReader
    
    println("nb lignes => " + dr.lines.length);
    println("nb mots => " + dr.words.length);
    println("nb true palindromes " + dr.newPalindromes.length)
    /*
    dr.newPalindromes.foreach((p) => println(p))
    */
    println("longest word : " + dr.longestWord)
  }
}