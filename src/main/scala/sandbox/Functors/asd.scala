package sandbox.Functors

import scala.concurrent.ExecutionContext

object asd extends App {

  import cats.Functor

  def lenOption: Option[String] => Option[Int] =
    Functor[Option].lift(_.length)

  val lensOption: Option[String] => Option[Int] =
    s => s match {
      case Some(value) => Some(value.length)
      case None => None
    }

  val something = Option("asd")
  assert(lenOption(something) == lensOption(something))
  assert(lenOption(None) == lensOption(None))

  import scala.concurrent.Future

  val a : Either[String] => Future[Int] =
    Functor[Future].lift(_.lenght)

}
