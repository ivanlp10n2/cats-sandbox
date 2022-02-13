package sandbox.scalaExercises

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AsyncWordSpec

class ScalaStdExTest extends AsyncWordSpec with Matchers {


  "A list " should {
    "return List(\"ABC\") " in {
      // It runs here but it didn't work
      def makeUpper(xs: List[String]) =
        xs map {
          _.toUpperCase
        }

      makeUpper(List("abc", "xyz", "123")) should be(List("ABC", "XYZ", "123"))
    }
    "return map with default values" in {
      //Good to remember
      val myMap2 =
        Map(
          "MI" -> "Michigan",
          "OH" -> "Ohio",
          "WI" -> "Wisconsin",
          "IA" -> "Iowa") withDefaultValue "missing data"
      myMap2("TX") should be(
        "missing data"
      )

      val c = 'a' //unicode for a
      val e = '\"'
      val f = '\\'

      "%c".format(c) should be("a")
      "%c".format(e) should be("\"")
      "%c".format(f) should be("\\")

    }

    "Scooby" in {
      case class Dog(name: String, breed: String)
      val d1 = Dog("Scooby", "Doberman")
      println(d1)
      d1.toString should be(
        "Dog(Scooby,Doberman)"
      )
    }

    "Big ints" in {
      import java.math.BigInteger
      implicit def Int2BigIntegerConvert(value: Int): BigInteger =
        new BigInteger(value.toString)

      def add(a: BigInteger, b: BigInteger) = a.add(b)

      add(Int2BigIntegerConvert(3), Int2BigIntegerConvert(6)) == Int2BigIntegerConvert(9) should be(true)

      add(3, 6) == 9 should be(false)
      add(3, 6) == Int2BigIntegerConvert(9) should be(true)

      add(3, 6) == (9: BigInteger) should be(true)
      add(3, 6).intValue == 9 should be(true)
    }
    "Coordinates" in {
      val xValues = 1 to 4
      val yValues = 1 to 2
      val coordinates = for {
        x <- xValues
        y <- yValues
      } yield (x, y)
      coordinates(4) should be((3, 1))
    }

    "zip all" in {
      val xs = List(3, 5, 9)
      val ys = List("Bob", "Ann")
      (xs zip ys) should be(List((3, "Bob"), (5, "Ann")))

      val xs1 = List(3, 5, 9)
      val ys1 = List("Bob", "Ann")
      xs1.zipAll(ys1, -1, "?") should be(List((3, "Bob"), (5, "Ann"), (9, "?")))
    }
    "Iterators sameElements less than 5 compare order. Higher don't" in {
      val xs = List("Manny", "Moe", "Jack")
      val ys = List("Manny", "Moe", "Jack")
      xs.iterator.sameElements(ys) should be(true)

      val xt = List("Manny", "Moe", "Jack")
      val yt = List("Manny", "Jack", "Moe")
      xt.iterator.sameElements(yt) should be(false)

      val xs1 = Set(3, 2, 1, 4, 5, 6, 7)
      val ys1 = Set(7, 2, 1, 4, 5, 6, 3)
      xs1.iterator.sameElements(ys1) should be(true)

      val xt1 = Set(1, 2, 3)
      val yt1 = Set(3, 2, 1)
      xt1.iterator.sameElements(yt1) should be(false)
    }
    "fold left" in {
      val list = List(5, 4, 3, 2, 1)
      val result = list.foldRight(0) { (`next element`, `running total`) =>
        `next element` - `running total`
      }
      result should be(3)

      val result2 = list.foldRight(0)(_ - _) //Short hand
      result2 should be(3)

      (5 - (4 - (3 - (2 - (1 - 0))))) should be(3)
    }
    "formating"in{
      def repeatedParameterMethod(x: Int, y: String, z: Any*) = {
        "%d %ss can give you %s".format(x, y, z.mkString(", "))
      }
      repeatedParameterMethod( 3, "egg", "a delicious sandwich", "protein", "high cholesterol") should be( "3 eggs can give you a delicious sandwich, protein, high cholesterol" )
    }
  }
}
