package sandbox

import cats.data.EitherT
import cats.effect._

object Algorithm extends IOApp {
  override def run(args: List[String]): IO[ExitCode] =
    program
      .
      .as(ExitCode.Success)
      .handleError(ExitCode.Error)

  def program: IO[Unit] = for {
    _ <- IO.println("Select the fibonacci nth number and press enter")
    n <- IO.readLine.map(_.toInt)
    result <- fib(n)
    _ <- IO.println("The number $n is $result")
  } yield ()

  def fib(n: Int): IO[Unit] = fib(3)

}
