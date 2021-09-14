package sandbox.Monads

object MonadsApp extends App {

  import cats.Monad
  import cats.syntax.flatMap._
  import cats.syntax.functor._

//  Int => F[Int]
  def sum[F[_] : Monad](a: F[Int], b: F[Int]): F[Int] =
    a flatMap (x => b map (y => x + y))

  import cats.instances.either._
  assert(sum(Option(1), Option(3)) == Option(4))
  assert(sum(List(1), List(3)) == List(4))
  assert(sum(
    Right(3): Either[Int, Int],
    Right(7): Either[Int, Int]) == Right(10))




  implicit val i: Int = 5

  def sum(x: Int) (implicit y: Int): Int = x + y

  println(sum(3))








  println(sum(List(1,2,3), List(1))) //List(2,3,4)
  println(sum(List(1,2,3), List(1,2))) //List(2, 3, 3, 4, 4, 5)

  /*
  {
    //  import Monad.Syntax._
    //  import Monad._
    trait Monad[M[_]] {
      def pure[A](value: A): M[A]

      def flatMap[A, B](value: M[A])(f: A => M[B]): M[B]

      def map[A, B](value: M[A])(f: A => B): M[B] =
        flatMap(value)(a => pure(f(a)))
    }
    object Monad {
      implicit val optionMonad: Monad[Option] = new Monad[Option] {
        override def pure[A](value: A): Option[A] = if (value == null) None else Some(value)

        override def flatMap[A, B](value: Option[A])(f: A => Option[B]): Option[B] =
          value match {
            case Some(value) => f(value)
            case None => None
          }
      }

      object Syntax {
//        implicit class OptionMonad[Opt[_], A](private val option: Opt) {
//          def pure[A](value: A)(implicit Opt: Monad[Opt]): Opt[A] = Opt.pure(value)

//          def map[A, B](value: A)(implicit Opt: Monad[Opt]): Opt[A] = Opt.pure(value)

        }
      }
    }
   */
  /**
  {
    import cats.Id
    val a: Id[String] = "asda"
    println(a)

    def pure[A](value: A): Id[A] = value
    def map[A, B](a: Id[A])(fab: A => B) : Id[B] =
      fab(a)
    def flatMap[A,B](a: Id[A])(fab: A => Id[B]): Id[B] =
      map(a)(fab)
  }
  */
}
