package sandbox.catsEffects
import cats.effect._
import cats.syntax.all._
import scala.concurrent.duration._

object ConcurrentStateVar extends IOApp{
  override def run(args: List[String]): IO[ExitCode] =
    (tickingApp, printinHello).parTupled
      .as(ExitCode.Success)

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
