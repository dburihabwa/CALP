import java.text.Normalizer
import scala.collection.GenSeq
import java.io.FileWriter
import scala.collection.immutable.Range

case class Reader(words: GenSeq[String]) {

	lazy val truePalindromes = getTruePalindromes;

	lazy val palindromes = getPalindromes;

	lazy val newPalindromes = getNewPalindromes;

	lazy val longestWordFoldLeft = getLongestWordFoldLeft;

	lazy val longestWordFoldRight = getLongestWordFoldRight

	lazy val reversedWords = reverse;

	lazy val longestWordFold = getLongestWordFold

	def getTruePalindromes(): GenSeq[String] = {
		words.filter(word => word.equals(word.reverse))
	}
	def getPalindromes(): GenSeq[String] = {
		words.filter(word => {
			val str = Normalizer.normalize(word.toLowerCase(), Normalizer.Form.NFD)
			val exp = "\\p{InCombiningDiacriticalMarks}+".r
			val normalized = exp.replaceAllIn(str, "")
			normalized.equals(normalized.reverse)
		})
	}
	def getNewPalindromes(): GenSeq[String] = {
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

	def complete(pattern: String): GenSeq[String] = {
		words.filter(word => word.matches(pattern))
	}

	def reverse(): GenSeq[String] = {
		words.filter(word => words.indexOf(word.reverse) != -1)
	}

	def getLongestWordFold(): String = {
		var result = words.head
		words.tail.fold(result)(compareLongest)
	}

	def bench(collname: String, toColl: Iterator[String] => GenSeq[String]): Unit = {
		val step = 20000
		val max = 400000

		val range = 1 to max by step
		var resultsPalindrome = "palindromes, "
		var resultsLongestWord =  "longest, "
		var resultsComplete = "complete, "
		var resultsReversed = "reversed, "
		for (i <- range) {
			val reader = new Reader(toColl(words.iterator.take(i)))
			var start = System.nanoTime()
			reader.truePalindromes
			var end = System.nanoTime()
			var elapsed = end - start
			resultsPalindrome += elapsed + ","
			start = System.nanoTime()
			reader.longestWordFold
			end = System.nanoTime()
			elapsed = end - start
			resultsLongestWord += elapsed + ","
			start = System.nanoTime()
			reader.complete("e..a.t")
			end = System.nanoTime()
			elapsed = end - start
			resultsComplete  += elapsed + ","
			start = System.nanoTime()
			reader.reversedWords
			end = System.nanoTime()
			elapsed = end - start
			resultsReversed  += elapsed + ","
			println("done for first" + i)
		}
		val writer = new FileWriter(("list2.csv"), true)
		writer.write(collname + ", ")
		writer.write(range.mkString(", "))
		writer.write("\n")
		writer.write(resultsPalindrome)
		writer.write("\n")
		writer.write(resultsLongestWord)
		writer.write("\n")
		writer.write(resultsComplete)
		writer.write("\n")
		writer.write(resultsReversed)
		writer.write("\n")

		writer.flush()
		writer.close()
	}
}