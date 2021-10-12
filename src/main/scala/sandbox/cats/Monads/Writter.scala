package sandbox.cats.Monads

object Writter extends App{
  import cats.data.Writer
  val log = Writer(Vector(
    "There was upon a time",
    "Did not worked"
  ), 1900)
  println(log)

  import cats.instances.vector._
//  implicit val vectorMonoid: Monoid[Vector] = new Monoid[Vector]{
//    override def empty: Vector = Vector.empty
//
//  }
  val a = Writer.value(1900)
  println(a)

  def slowly[A](body: => A) =
    try body finally Thread.sleep(100)

  import cats.syntax.applicative._
  import cats.instances.vector._
  type Logged[A] = Writer[Vector[String], A]
  def factorial(n: Int): Logged[Int] = {
    import cats.syntax.writer._
    for{
      res <-
          if (n == 1) n.pure[Logged]
          else slowly(
            factorial(n - 1)
              .map(_ * n)
          )
      _  <- Vector(s"fact $n: $res ").tell
    } yield res
  }

  import scala.concurrent.Await
  import scala.concurrent.Future
  import scala.concurrent.duration._
  implicit val ec = scala.concurrent.ExecutionContext.global
  val logs = Await.result(
    Future.sequence(Vector(
      Future(factorial(5)),
      Future(factorial(5))
    ).map(f => f.map(log => log.written.reverse))), 5.seconds)

  println(logs)
}
