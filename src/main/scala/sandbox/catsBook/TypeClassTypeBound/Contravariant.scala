package sandbox.catsBook.TypeClassTypeBound

object Contravariant extends App {

  case class Money(amount: Int)

  val showMoney: Show[Money] = Show.show[Money](m => s"Money: $$${m.amount}")

  assert(showMoney.show(Money(3)) == s"Money: $$3")

  object VarianceSumTypes {

    // Covariant Sum Types

    /**
     * Humano <= Trabajador
     *
     * Humano = Trabajador?
     * Trabajador = Humano?
     *
     * depends on variance
     *
     * if Humano is covariance with Trabajador
     * then List[Trabajador] =
     */

    sealed trait Maybe[-A]

    final case class Full[A](value: A) extends Maybe[A]

    final case object Empty extends Maybe[Nothing]


    val perhaps = Full[Long](Int.MaxValue)
    //val perhaps = Full[Int](Long.maxValue)
    println(perhaps)

    sealed trait Sum[+A, +B] {
      def flatMap[AA >: A, C](f: B => Sum[AA, C]): Sum[AA, C] = this match {
        case Success(value) => f(value)
        case Failure(value) => Failure(value)
      }

      def map[AA >: A, C](f: B => C): Sum[AA, C] = this match {
        case Success(value) => Success(f(value))
        case Failure(value) => Failure(value)
      }

      def fold[AA >: A, BB >: B, C](error: A => C)(success: BB => C): C =
        this match {
          case Success(value) => success(value)
          case Failure(value) => error(value)
        }
    }

    final case class Success[B](value: B) extends Sum[Nothing, B]

    final case class Failure[A](value: A) extends Sum[A, Nothing]

    //functions are :
    //  contravariant in its input parameters
    //  covariant in its output values
    case class Box[+A](value: A) {
      def set[AA >: A](a: AA): Box[AA] = Box(a)
    }

    println(Success(3).flatMap(i => Success(i + 3)))
  }

}
