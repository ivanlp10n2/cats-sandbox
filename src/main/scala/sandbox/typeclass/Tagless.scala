package sandbox.typeclass

import cats.effect._
object TaglessApp extends IOApp{
  override def run(args: List[String]): IO[ExitCode] = program

  def program[F[_]: Monad: Sync] =
    F.delay(
      F.flatMap(a => a)
    )
}
