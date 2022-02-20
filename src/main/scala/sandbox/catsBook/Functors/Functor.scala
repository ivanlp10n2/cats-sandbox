package sandbox.catsBook.Functors

object FunctorApp extends App {

  /*
  { // Functions as Functor
    import cats.syntax.functor._
    val func =
      (() => 3)
        .map(x => x + 1)
        .map(x => x + 2)
        .map(x => s"$x!")

    assert("6!" == func())
  }

  /**
   * We should think of map not as an iteration pattern,
   * but as a way of sequencing computations on values
   * ignoring some complication dictated by the relevant
   * data type:
   * - Option = the value may or may not be present;
   * - Either = there may be a value or an error;
   * - List   = there may be zero or more values.
   */

  {
    import cats.syntax.functor._
    val func1 = (a: Int) => a + 1
    val func2 = (a: Int) => a * 2
    val func3 = (a: Int) => s"$a!"
    val func4 = func1 map func2 map func3

    println(func4(123))

    import cats.Functor
    def doMath[F[_]: Functor](start: F[Int])
                    (implicit functor: Functor[F]): F[Int] =
      functor.map(start)(n => n + 1 * 2)

    println(doMath(List(32, 33)))
    println(doMath(Option(33)))
  }
*/
  {
    // Sum type y Product type son ADT: Algebraic data types
    // Abstract ADT
  sealed trait Tree[+A] // Branch OR Leaf
  case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
  // left AND right

  case class Leaf[A](value: A) extends Tree[A]

  trait Functor[F[_]] {
    def map[A, B](fa: F[A])(f: A => B): F[B]
  }

  val treeFunctor: Functor[Tree] = new Functor[Tree] {
    override def map[A, B](fa: Tree[A])(f: A => B): Tree[B] =
      fa match {
        case Leaf(value) => Leaf(f(value))
        case Branch(left, right) => Branch(map(left)(f), map(right)(f))
      }
  }

  
  val mapeableTree : Tree[Int]=
    Branch(
      Branch(
        Leaf(4),
        Leaf(3)
      ),
      Leaf(10)
    )

  treeFunctor.map(mapeableTree)(a => a + 2)
  //
  //    assert(
  //      implicitly[Functor[Tree]].map(mapeableTree)(_ + 1) ==
  //        mapeableTree.map(a => a + 1)
  //    )

}

}
