package sandbox.cats.Functors

object asd extends App {

  import cats.Functor

  val lenOption: Option[String] => Option[Int] = x =>
    Functor[Option].lift((s: String) => s.length)(x)

  val lensOption: Option[String] => Option[Int] =
    s => s match {
      case Some(value) => Some(value.length)
      case None => None
    }

  val something = Option("asd")
  assert(lenOption(something) == lensOption(something))
  assert(lenOption(None) == lensOption(None))

  val animals = List("Cats", "are", "awesome")

  
//  def len[A,B] : Functor[] = {
//    Functor[]
//      .lift[A, B](A => a => animals)
//  }

}