package sandbox.cats.Monads

object TheoryMonadTransformer extends App{
  import scala.concurrent.Future
  import cats.data.EitherT
  import scala.concurrent.ExecutionContext.Implicits.global

  type Response[A] = EitherT[Future, String, A]

  def getPowerLevel(autobot: String): Response[Int] =
      powerLevels.get(autobot) match {
        case Some(value) => EitherT.right(Future(value))
        case None => EitherT.left(Future("non reacheable"))
      }

  def canSpecialMove(ally1: String, ally2: String): Response[Boolean] =
    for {
      x <- getPowerLevel(ally1)
      y <- getPowerLevel(ally2)
    } yield (x + y) > 15

  import scala.concurrent.Await
  import scala.concurrent.duration._
  def tacticalReport(ally1: String, ally2: String): String = {
    val stack = canSpecialMove(ally1, ally2).value

    Await.result(stack, 1.second) match {
      case Left(value) => value
      case Right(true) => s"$ally1 and $ally2 are ready to roll out!"
      case Right(false) => s"$ally1 and $ally2 need a recharge!"
    }
  }


  implicit val powerLevels = Map(
    "Jazz"        -> 6,
    "Bumblebee"   -> 8,
    "Hot Rod"     -> 10
  )

  println(getPowerLevel("Ivan"))

}
