package sandbox.Monads

object StateApp extends App {

  import cats.data.State

  val a = State[Int, String](s => (s, s"The state is $s"))
  println(a.run(40).value)

}

object StatePractice extends App {

  import cats.data.State

  type CalcState[A] = State[List[Int], A]

  def evalOne(symbol: String): CalcState[Int] =
    symbol match {
      case "+" => operator(_ + _)
      case "-" => operator(_ - _)
      case "*" => operator(_ * _)
      case "/" => operator(_ / _)
      case num => operand(num.toInt)
    }

  def operator(f: (Int, Int) => Int): CalcState[Int] =
    State(s => s match {
      case first :: second :: tail =>
        val result = f(first, second)
        (result :: tail, result)
      case _ => sys.error("No value in state")
    })

  def operand(n: Int): CalcState[Int] =
    State(s => (n :: s, n))

  assert(evalOne("+").runA(List(1, 2)).value == 3)
  assert(evalOne("43").runA(Nil).value == 43)

  println(
    (for {
      _ <- evalOne("10")
      _ <- evalOne("10")
      ans <- evalOne("*")
    } yield ans)
      .run(List.empty)
      .value
  )

  def evalAll(input: List[String]): CalcState[Int] = {
    input.foldLeft(State.empty: CalcState[Int]) { (a, b) =>
      a.flatMap(_ => evalOne(b))
    }
  }

  def evalInput(input: String): Int =
    evalAll( input
        .split(" ")
        .toList)
      .runA(Nil)
      .value

  println(evalInput("0 2 + 3 4 - 5 + *"))
  //    State( s =>
  //      for{
  //        symbol <- input
  //        result <- evalOne(symbol)
  //      } yield (s, result)
  //    )
}

