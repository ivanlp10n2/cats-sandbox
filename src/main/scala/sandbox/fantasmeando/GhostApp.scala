package sandbox.fantasmeando

import cats.effect.{ExitCode, IO, IOApp}

object GhostApp extends IOApp{
  override def run(args: List[String]): IO[ExitCode] = IO(ExitCode.Success)

//  sealed trait StringOrNumber
//  case object S extends String
}
