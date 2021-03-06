package sandbox.catsBook.Monads

object MonadAgain extends App{

  trait Monad[F[_]] {
    def pure[A](a: A): F[A]

    def flatMap[A, B](value: F[A])(func: A => F[B]): F[B]

    def map[A, B](value: F[A])(func: A => B): F[B] =
      flatMap(value)(a => pure(func(a)))
  }

  import cats.Id
  implicit val idMonad: Monad[Id] = new Monad[Id]{
    override def pure[A](a: A): Id[A] = a

    override def map[A, B](value: Id[A])(func: A => B): Id[B] = flatMap(value)(func(_))

    override def flatMap[A, B](value: Id[A])(func: A => Id[B]): Id[B] = func(value)
  }
}
