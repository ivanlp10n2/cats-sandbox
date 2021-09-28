package sandbox.Monads

object EvalTheory extends App{
  import cats.Eval
  val saying = Eval
    .always(println("doin always"))
    .map(_ => println("then do something and memoize these results"))
    .memoize
    .map(_ => println("then do this always"))

  saying.value
  saying.value
  saying.value
}
object StackSafety extends App{
  
}
