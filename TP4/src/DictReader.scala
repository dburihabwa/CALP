import java.io.InputStream
import scala.io.BufferedSource
import java.io.FileInputStream
import scala.io.BufferedSource
import java.text.Normalizer
import java.util.GregorianCalendar;

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

	def compareLongest(a: String, b: String): String = {
		if (a.length() < b.length())
			return b
		else
			return a
	}

	def getLongestWord(): String = {
		var result = words.head
		words.tail.foldLeft(result)(compareLongest)
	}

	def getLongestWordRight(): String = {
		var result = words.last
		words.init.foldRight(result)(compareLongest)
	}

}
object Hi extends App {
	override def main(args: Array[String]): Unit = {
		val dr = new DictReader

		println("nb lignes => " + dr.lines.length);
		println("nb mots => " + dr.words.length);
		println("nb true palindromes " + dr.newPalindromes.length)
		var start = new GregorianCalendar();
		var longestWord = dr.longestWord;
		var end = new GregorianCalendar();
		println("longest word (foldLeft): " + longestWord + " (" + (end.getTimeInMillis() - start.getTimeInMillis()) + " ms)")
		start = new GregorianCalendar();
		longestWord = dr.getLongestWordRight;
		end = new GregorianCalendar();
		println("longest word (foldRight): " + longestWord + " (" + (end.getTimeInMillis() - start.getTimeInMillis()) + " ms)")
	}
}