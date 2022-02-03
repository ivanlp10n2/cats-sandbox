package sandbox

import cats.effect._


object Algorithm extends IOApp {
  override def run(args: List[String]): IO[ExitCode] =
    program
      .as(ExitCode.Success)
      .handleError(_ => ExitCode.Error)

  def program: IO[Unit] = for {
    _ <- IO.println("Select the fibonacci nth number and press enter")
//    n <- IO.readLine.map(_.toInt)
//    result <- Fibonacci.apply(n)
//    _ <- IO.println(s"The number $n is $result")
  } yield ()

}

