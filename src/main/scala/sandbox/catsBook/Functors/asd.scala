package sandbox.catsBook.Functors


object asd extends App {

  val lenOption: Option[String] => Option[Int] = x =>
    cats.Functor[Option].lift((s: String) => s.length)(x)

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

//  F[_] => A => F[A]
//  List => Int => List[Int]
//
//  Option(3) ?? Some(3) || None
//  F => Int => F[Int]
//  Option(3)  map (a: Int => f(a))
//  (F => Int) map (a :Int => f(a))

  trait Functor{
    def map[A, B](l: List[A])(f: A => B): List[B]
  }

  // prefix:   Either[A, B]
  // infix:    A Either B
  // postfix:  [A,B] Either
  val a: Functor = new Functor{
    def map[A, B](l :List[A])(f: A => B): List[B] =
      l match {
        case head :: tail => f(head) :: map(tail)(f)
        case Nil => Nil
      }
  }

  println(a)
}










