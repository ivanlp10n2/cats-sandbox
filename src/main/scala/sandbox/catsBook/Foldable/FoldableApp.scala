package sandbox.catsBook.Foldable

object FoldableApp extends App{

  val a = List(1,2,3)
  println("fold right" + a.foldRight[List[Int]](List.empty)((n, list) =>  n :: list))
  println("fold Left" + a.foldLeft[List[Int]](List.empty)((list, n) =>  n :: list))

  import cats.syntax.semigroup._
  def susFlatMap[A,B](l: List[A])(f: A => List[B]): List[B] =
    l.foldRight(List.empty[B])( (a, b) => f(a) |+| b)

  def susmap[A,B](l: List[A])(f: A => B): List[B] =
    l.foldRight(List.empty[B])( (a, b) => f(a) :: b)

  def filter[A](l: List[A])(f: A => Boolean): List[A] =
    l.foldRight(List.empty[A])( (a,b) => if (f(a)) a :: b else b)

  import cats.Monoid
  def sum[A](l: List[A])(implicit monoid: Monoid[A]): A =
    l.foldRight(monoid.empty)(monoid.combine)


  println(susFlatMap(List(1, 2, 3))(a => List(a, a * 10, a * 100)))

  import cats.Foldable
  Foldable[Option].foldLeft(Option(12), 0)( (a,b) => a + b)
  println(Foldable[Option].forall(Option(3))(a => a > 4))

  final case class Box[A](a: A) {
    def map[B](f: A => B): Box[B] = Box(f(a))

    def contraMap[B](f: B => A): Box[B] = ???
  }

  trait Predicate[A] { self =>
    def test(a: A): Boolean

    final def contraMap[B](f: B => A): Predicate[B] = new Predicate[B] {
      override def test(a: B): Boolean = self.test(f(a))
    }

    final def map[B](f: A => B): Predicate[B] = ??? // Is this even possible?
  }
  trait Stringy[A] {
    def stringy(value: A): String
  }
  object Stringy {
    def contraMap[A, B](sa: Stringy[A])(f: B => A): Stringy[B] =
      new Stringy[B] {
        override def stringy(value: B): String = sa.stringy(f(value))
      }
  }

}
