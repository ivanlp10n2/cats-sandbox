package sandbox.ytpractices

import cats.syntax.all._

object Either extends App {

  case class DividedByZero(dividend: Int)

  def divide(dividend: Int, divisor: Int): DividedByZero Either Int =
    if (divisor == 0) Left(DividedByZero(dividend))
    else Right(dividend / divisor)

  case class NoLogarithm(number: Double)

  def log10(number: Double): NoLogarithm Either Double =
    if (number <= 0) Left(NoLogarithm(number))
    else Right(Math.log10(number))

  /// Mixing errors: They are not the same Error type => compose issues
  // 3 ways with data
  object Pepiot{
    sealed trait DividedByZeroOrNoLogarithm
    case class LDividedByZero(dividend: Int) extends DividedByZeroOrNoLogarithm
    case class LNoLogarithm(number: Double) extends DividedByZeroOrNoLogarithm

    def divideAndLog(diviend: Int, divisor: Int): DividedByZeroOrNoLogarithm Either Double =
      for {
        divideResult <- divide(diviend, divisor).leftMap {
          case DividedByZero(dividend) => LDividedByZero(dividend)
        }
        logResult <- log10(divideResult.toDouble).leftMap {
          case NoLogarithm(number) => LNoLogarithm(number)
        }
      } yield logResult
  }

  object Pepito2{
    /**
     * If you need different composition of errors, error representation are coupled
     */
    type DividedByZeroOrNoLogarithm = DividedByZero Either NoLogarithm

    def divideAndLog(diviend: Int, divisor: Int): DividedByZeroOrNoLogarithm Either Double =
      for {
        divideResult <- divide(diviend, divisor).leftMap(Left(_))
        logResult <- log10(divideResult.toDouble).leftMap(Right(_))
      } yield logResult
  }

  object Pepito3{ // Monad transformer version
    /**
     * Same problem with order
     */
    type DividedByZeroOrNoLogarithm[A] = DividedByZero Either (NoLogarithm Either A)

    def divideAndLog(diviend: Int, divisor: Int): DividedByZeroOrNoLogarithm[Double] =
      divide(diviend, divisor).map(a => log10(a.toDouble))
  }

  /**
   * the problem with these 3 approachs are data:
   * With data, you always construct them and deconstruct them to consume its values
   */

  // Solutions :

  object Solution1{
    type DividedByZeroOrNoLogarithm = DividedByZero Either NoLogarithm
    trait Inject[E, S] {
      def apply(s: S): E
    }
    case class ErrorCualquiera()

    def divideAndLog[E](dividend: Int, divisor: Int
                       )(implicit div: Inject[E, DividedByZero],
                         log: Inject[E, NoLogarithm]): E Either Double =
      for {
        divideResult <- divide(dividend, divisor).leftMap(div.apply(_))
        logResult <- log10(divideResult.toDouble).leftMap(log.apply(_))
      } yield logResult

      implicit def z: Inject[ErrorCualquiera, DividedByZero] = new Inject[ErrorCualquiera, DividedByZero] {
        override def apply(s: DividedByZero): ErrorCualquiera = ErrorCualquiera()
      }
      implicit def x: Inject[ErrorCualquiera, NoLogarithm] = new Inject[ErrorCualquiera, NoLogarithm] {
        override def apply(s: NoLogarithm): ErrorCualquiera = ErrorCualquiera()
      }

      println(divideAndLog[ErrorCualquiera](3, 2))
  }

  Solution1

}
