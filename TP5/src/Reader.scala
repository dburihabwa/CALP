import java.text.Normalizer

import scala.collection.GenSeq

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
    words.filter(word => words .indexOf(word.reverse) != -1)
  }

  def getLongestWordFold(): String = {
    var result = words.head
    words.tail.fold(result)(compareLongest)
  }
}