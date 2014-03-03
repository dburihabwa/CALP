import java.io.InputStream
import scala.io.BufferedSource
import java.io.FileInputStream
import scala.io.BufferedSource
import java.text.Normalizer
import java.util.GregorianCalendar
import scala.collection.GenSeq
import scala.collection.parallel.immutable.ParVector

class DictReader(filename: String = "/usr/share/hunspell/fr.dic") {
  val lines: List[String] = getLinesFromFile;
  val words: List[String] = getWordsFromLines;
  val wordsSet: Set[String] = words.toSet;

  lazy val truePalindromes = getTruePalindromes;

  lazy val palindromes = getPalindromes;

  lazy val newPalindromes = getNewPalindromes;

  lazy val longestWordFoldLeft = getLongestWordFoldLeft;

  lazy val longestWordFoldRight = getLongestWordFoldRight

  lazy val reversedWordsList = reverseFromList;

  lazy val reversedWordsSet = reverseFromSet;

  lazy val longestWordFold = getLongestWordFold

  def getLinesFromFile(): List[String] = {
    val file = new BufferedSource(new FileInputStream(filename))
    file.getLines.toList.tail
  }

  def getWordsFromLines(): List[String] = {
    lines.map(line => line.split("/")(0))
  }

  def getSetFromWords(): Set[String] = {
    getWordsFromLines.toSet
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

  def compareLongest(a: String, b: String): String = {
    if (a.length() < b.length())
      return b
    else
      return a
  }

  def getLongestWordFoldLeft(): String = {
    var result = words.head
    words.tail.foldLeft(result)(compareLongest)
  }

  def getLongestWordFoldRight(): String = {
    var result = words.last
    words.init.foldRight(result)(compareLongest)
  }

  def complete(pattern: String): List[String] = {
    words.filter(word => word.matches(pattern))
  }

  def reverseFromList(): List[String] = {
    words.filter(word => words contains word.reverse)
  }

  def reverseFromSet(): Set[String] = {

    wordsSet.filter(word => wordsSet contains word.reverse)
  }

  def getLongestWordFold(): String = {
    var result = words.head
    words.tail.fold(result)(compareLongest)
  }
}
object Hi extends App {
  override def main(args: Array[String]): Unit = {
    val dr = new DictReader
//
//    println("nb lignes => " + dr.lines.length);
//    println("nb mots => " + dr.words.length);
//    println("nb true palindromes " + dr.newPalindromes.length)
//    var start = new GregorianCalendar();
//    var longestWord = dr.longestWordFoldLeft;
//    var end = new GregorianCalendar();
//    println("longest word (foldLeft): " + longestWord + " (" + (end.getTimeInMillis() - start.getTimeInMillis()) + " ms)")
//    start = new GregorianCalendar();
//    longestWord = dr.longestWordFoldRight;
//    end = new GregorianCalendar();
//    println("longest word (foldRight): " + longestWord + " (" + (end.getTimeInMillis() - start.getTimeInMillis()) + " ms)")

    //println(dr.complete("e..a.t"))

    //    start = new GregorianCalendar();
    //    val reverseList = dr.reversedWordsList
    //    end = new GregorianCalendar();
    //    println("reverse word (List): (" + (end.getTimeInMillis() - start.getTimeInMillis()) + " ms)")
    //    
    //    start = new GregorianCalendar();
    //    val reverseSet = dr.reversedWordsSet
    //    end = new GregorianCalendar();
    //    println("reverse word (Set): (" + (end.getTimeInMillis() - start.getTimeInMillis()) + " ms)")
    //    println("reverseSet.size : " + reverseSet.size)
    //    println("reverseList.size : " + reverseList.size)
//    start = new GregorianCalendar();
//    longestWord = dr.longestWordFold;
//    end = new GregorianCalendar();
//    println("longest word (fold): " + longestWord + " (" + (end.getTimeInMillis() - start.getTimeInMillis()) + " ms)")
    //bench(dr.words.toStream)
    bench(dr.words.toList)
    bench(dr.words.toVector)
    val parVector = dr.words.toVector.par
    bench(parVector)
  }
  
  def bench(words : GenSeq[String]): Unit= {
    var dr = new Reader(words)
    println("Implémentation: ")
    
    println("nb true palindromes " + dr.newPalindromes.length)
    var start = new GregorianCalendar();
    var longestWord = dr.longestWordFoldLeft;
    var end = new GregorianCalendar();
    println("longest word (foldLeft): " + longestWord + " (" + (end.getTimeInMillis() - start.getTimeInMillis()) + " ms)")
    start = new GregorianCalendar();
    longestWord = dr.longestWordFoldRight;
    end = new GregorianCalendar();
    println("longest word (foldRight): " + longestWord + " (" + (end.getTimeInMillis() - start.getTimeInMillis()) + " ms)")
    start = new GregorianCalendar();
    longestWord = dr.longestWordFold;
    end = new GregorianCalendar();
    println("longest word (fold): " + longestWord + " (" + (end.getTimeInMillis() - start.getTimeInMillis()) + " ms)")

    start = new GregorianCalendar();
    val completion = dr.complete("e..a.t")
    end = new GregorianCalendar();
    println("complete : (" + (end.getTimeInMillis() - start.getTimeInMillis()) + " ms)")

    start = new GregorianCalendar();
    val reverseList = dr.reversedWords
    end = new GregorianCalendar();
    println("reverse word : (" + (end.getTimeInMillis() - start.getTimeInMillis()) + " ms)")
  }
}