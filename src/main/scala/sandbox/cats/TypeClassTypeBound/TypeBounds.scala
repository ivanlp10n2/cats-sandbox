package sandbox.cats.TypeClassTypeBound

import Contravariant.VarianceSumTypes._
object TypeBounds extends App {

  sealed trait Expression {
    def eval: Sum[String, Double] = this match {
      case Addition(left, right) => operate(left, right)((l, r) => Success(l + r))
      case Subtraction(left, right) => operate(left, right)((l, r) => Success(l - r))
      case Division(left, right) => right.eval.flatMap(n => {
        if (n == 0) Failure("Division by zero")
        else left.eval flatMap (l => Success(l / n))
      })
      case SquareRoot(value) => value.eval.flatMap(n =>
        if (n < 0) Failure("Square root of negative number")
        else Success(Math.sqrt(n)))
      case Number(value) => Success(value)
    }
  }

  def operate(left: Expression, right: Expression)
             (f: (Double, Double) => Sum[String, Double]): Sum[String, Double] =
    left.eval flatMap (l =>
      right.eval flatMap (r =>
        f(l, r)))

  final case class Addition(left: Expression, right: Expression) extends Expression

  final case class Subtraction(left: Expression, right: Expression) extends Expression

  final case class Division(left: Expression, right: Expression) extends Expression

  final case class SquareRoot(value: Expression) extends Expression

  final case class Number(value: Double) extends Expression

  assert(Addition(Number(1), Number(2)).eval == Success(3))
  assert(SquareRoot(Number(-1)).eval == Failure("Square root of negative number"))
  assert(Division(Number(4), Number(0)).eval == Failure("Division by zero"))
  Division(
    Addition(
      Subtraction(
        Number(8),
        Number(6)
      ),
      Number(2)
    ),
    Number(2)
  ).eval.map(a => println(a))
  assert(
    Division(
      Addition(
        Subtraction(
          Number(8),
          Number(6)),
        Number(2)),
      Number(2)
    ).eval == Success(2.0))

}
