package sandbox.Monads

object CustomMonads extends App{
  import cats.Monad
  val optionMonad = new Monad[Option]{
    override def pure[A](x: A): Option[A] = if (x == null) None else Some(x)

    override def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] = fa.flatMap(f)

    override def tailRecM[A, B](a: A)(f: A => Option[Either[A, B]]): Option[B] = f(a) match {
      case Some(Right(value)) => Some(value)
      case Some(Left(value))  => tailRecM(value)(f)
      case None => None
    }
  }

  import cats.syntax.functor._
  def retry[F[_]: Monad, A](initial: A)(f: A => F[A]): F[A] =
    Monad[F].tailRecM(initial)(a => f(a).map(Left(_)))

  println(retry(1000000)(a => if (a == 0) None else Some(a - 1)))


  sealed trait Tree[+A]

  final case class Branch[A](left: Tree[A], right: Tree[A])
    extends Tree[A]

  final case class Leaf[A](value: A) extends Tree[A]

  def branch[A](left: Tree[A], right: Tree[A]): Tree[A] =
    Branch(left, right)

  def leaf[A](value: A): Tree[A] =
    Leaf(value)

//  val monadTree = new Monad[Tree] {
//    override def pure[A](x: A): Tree[A] = Leaf(x)
//    override def flatMap[A, B](fa: Tree[A])(f: A => Tree[B]): Tree[B] =
//      fa match {
//        case Branch(left, right) => f( flatMap(left)(f), flatMap(right)(f))
//        case Leaf(value) => f(value)
//      }
//
//    override def tailRecM[A, B](a: A)(f: A => Tree[Either[A, B]]): Tree[B] =
//      f(a) match {
//        case Branch(left, right) =>
//        case Leaf(value) =>
//      }
//  }

}
