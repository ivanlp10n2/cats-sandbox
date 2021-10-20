package sandbox.catsEffects

import cats.effect._
import TimmingApp._
object Cancel extends IOApp{
  override def run(args: List[String]): IO[ExitCode] =
    for {
      fiber <- task.onCancel(IO("I was cancelled").debug.void).start
      _ <- IO("pre-cancel").debug
      _ <- fiber.cancel
      _ <- IO("post-cancel").debug
    } yield ExitCode.Success

//    import scala.concurrent.duration._
//  def task: IO[String] = IO.sleep(2.seconds) *> IO("task").debug
    def task: IO[String] = IO("task").debug *> IO.never

}
