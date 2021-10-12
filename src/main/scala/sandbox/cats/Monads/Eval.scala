package sandbox.cats.Monads

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

object ValidateApp extends App{
  def validateAdult(age: Int): Boolean=
    (age >= 18)

  import cats.MonadError
  def validateAdult[F[_]](age: Int)(implicit me: MonadError[F, Throwable]): F[Int] =
    me.ensure(me.pure(age))(new IllegalArgumentException("no es mayor"))(age => age >= 18)
//    if (age >= 18) me.pure(age)
//    else me.raiseError(new IllegalArgumentException("no es mayor"))


    import scala.Either
    type EitherOr[A] = Either[Throwable, A]
    println(validateAdult[EitherOr](18))
}

object StackSafety extends App{
  def factorial(n: BigInt): BigInt =
    if (n == 1) n
//    else factorial(n - 1, n * acc)
        else n * factorial(n - 1)

    println(factorial(40000))

  import cats.Eval
  def factorialEval(n: BigInt): Eval[BigInt] =
    if (n == 1) Eval.now(n)
    else Eval.defer(factorialEval(n - 1))
      .map(_ * n)

//    println(factorialEval(40000).value)


  def foldRight[A, B](as: List[A], acc: B)
                     (f: (A, B) => B) : B =
    foldRightEval(as, Eval.now(acc))( (a,b) =>
      b.map( f(a, _))
    ).value

  def foldRightEval[A,B](as: List[A], acc: Eval[B])
                        (f: (A, Eval[B]) => Eval[B]): Eval[B] =
    as match {
      case head :: tail =>
        Eval.defer( foldRightEval(tail, f(head, acc))(f))
      case Nil =>
        acc
    }

}
