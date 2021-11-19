package sandbox.catsEffects
import cats.effect._
import cats.syntax.all._

import scala.concurrent.duration._

object ConcurrentStateVar extends IOApp{
  override def run(args: List[String]): IO[ExitCode] = {
    //flatmap :: F[A] > A => F[B] > F[B]
    (tickingApp, printinHello).parTupled
      .as(ExitCode.Success)
  }

  // Never ends ticking app
//  override def run(args: List[String]): IO[ExitCode] =
//    tickingApp *> printinHello *>
//      IO(ExitCode.Success)

  def tickingApp: IO[Unit] =
    for{
      _ <- IO.println("tick")
      _ <- IO.sleep(1.seconds)
      _ <- tickingApp
    } yield ()

  def printinHello: IO[Unit] =
    for{
      _ <- IO.println("hello")
      _ <- IO.sleep(4.seconds)
      _ <- printinHello
    } yield ()
}

object RefUpdateImpure extends IOApp{
//  import cats.effect.implicits._
  import cats.implicits._
  override def run(args: List[String]): IO[ExitCode] =
    for{
      ref <- Ref[IO].of(0)
      _   <- List(1,2,3).traverse(a => task(a, ref))
    } yield ExitCode.Success

  def task(id: Int, state: Ref[IO, Int]): IO[Unit] =
    state
      .modify(prev => id -> println(s"$prev->$id"))
      .replicateA(4)
      .void
}
