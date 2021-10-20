package sandbox.catsEffects

import cats.effect.{ExitCode, IO, IOApp}

object HelloWorldApp extends IOApp{
  override def run(args: List[String]): IO[ExitCode] =
    helloWorld.as(ExitCode.Success)

  def helloWorld: IO[Unit] = IO(println("que onda"))

}
