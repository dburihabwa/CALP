import org.scalatest._

class ExampleSpec extends FlatSpec with Matchers {

  "Hello world" should "return" in {
    Hi.main(Array()) should be()
  }

  
}