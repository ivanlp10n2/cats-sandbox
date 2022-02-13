package sandbox.scalaExercises

object ScalaStdEx extends App {
  object A {
    val a = "asd"
    val b = "asd"
  }

  println(A.a eq A.b)

  def makeUpper(xs: List[String]) =
    xs map {
      _.toUpperCase
    }

  def makeWhatEverYouLike(xs: List[String], sideEffect: String => String) =
    xs map sideEffect

//  println(makeUpper(List("abc", "xyz", "123")))

  case class Dog(name: String)
  println(Dog("jorge"))
}