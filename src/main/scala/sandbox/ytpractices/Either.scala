package sandbox.ytpractices

import cats.syntax.all._

object Either extends App {

  object withData {
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
    object Pepiot {
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

    object Pepito2 {
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

    object Pepito3 { // Monad transformer version
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

    object Solution1 {
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

        implicit def y: Inject[ErrorCualquiera, DividedByZero] = new Inject[ErrorCualquiera, DividedByZero] {
          override def apply(s: DividedByZero): ErrorCualquiera = ErrorCualquiera()
        }

        implicit def x: Inject[ErrorCualquiera, NoLogarithm] = new Inject[ErrorCualquiera, NoLogarithm] {
          override def apply(s: NoLogarithm): ErrorCualquiera = ErrorCualquiera()
        }

        println(divideAndLog[ErrorCualquiera](3, 2))
    }
  }


  object withFunctions { // Equivalent in the sense of working with data. function avoids alocation
    def produceInt: Int = 1
    def consumeIntConsumer[A](consumer: Int => A): A = consumer(1)

    val sum = produceInt + 2
    val sum2 = consumeIntConsumer(n => n + 2)

    val sum3 = produceInt + produceInt + 2
    val sum4 = consumeIntConsumer(_ + consumeIntConsumer(_ + 2))

    // Same program without data structures
    def divide[A](dividend: Int, divisor: Int, dividedByZero: Int => A, result: Int => A): A =
      if(divisor == 0) dividedByZero(divisor)
      else result(dividend / divisor)

    def log10[A](number: Double, noLogarithm: Double => A, result: Double => A)=
      if(number <= 0) noLogarithm(number)
      else result(Math.log10(number))

    def divideAndLog10[A](dividend: Int, divisor: Int, dividedByZero: Int => A, noLogarithm: Double => A, result: Double => A): A =
      divide(dividend, divisor, dividedByZero, {number => log10(number.toDouble , noLogarithm, result) })

    divideAndLog10(5, 5,
      dividedByZero = _ => println("dividiste x 0 bigote"),
      noLogarithm = _ => println("value = 0 non logarithm"),
      result = n => println(s"Result $n"))
    // callback hell, nesting to the right forever >:c
    // Stack can blow up
    // non performant because JVM doesn't know what could be there nor inline.

  }

  object withMonads{ // Continuation passing style is represented by monads
    import cats.Monad

    def divide[F[_]: Monad](dividend: Int, divisor: Int, dividedByZero: Int => F[Int]): F[Int] =
      if (divisor == 0) dividedByZero(divisor)
      else (dividend / divisor).pure[F]

    def log10[F[_]: Monad](number: Double, noLogarithm: Double => F[Double]): F[Double] =
      if (number <= 0) noLogarithm(number)
      else Math.log10(number).pure[F]

    def divideAndLog10[F[_]: Monad, A](dividend: Int, divisor: Int, divideByZero: Int => F[*], noLogarithm: Double => F[A]): F[A]=
      for{
        divideResult <- divide[F](dividend, divisor, divideByZero)
        logResult <- log10[F](divideResult.toDouble, noLogarithm)
      } yield logResult

    divideAndLog10(5, 5,
      dividedByZero = _ => println("dividiste x 0 bigote"),
      noLogarithm = _ => println("value = 0 non logarithm"),
      result = n => println(s"Result $n"))
  }

  withFunctions


}
