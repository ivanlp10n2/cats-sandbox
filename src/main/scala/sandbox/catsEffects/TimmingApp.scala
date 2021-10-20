package sandbox.catsEffects

import scala.concurrent.duration.FiniteDuration

import cats.effect._

object TimmingApp extends IOApp {

  def clock: IO[Long] = IO(System.currentTimeMillis())

  import scala.concurrent.duration._

  def time[A](action: IO[A]): IO[(FiniteDuration, A)] =
    clock.flatMap(start =>
      action.flatMap(run =>
        clock.flatMap(end =>
          IO(((end - start).millisecond, run)))))

  val timedHello = TimmingApp.time(IO("Hello"))
//  println(timedHello.unsafeRunSync())
//  timedHello.unsafeRunSync() match {
//    case (duration, _) => println(s"'hello' took $duration")
//  }

//  val tickingClock: IO[Unit] =
//    for {
//      _ <- IO(println(s"time is ${System.currentTimeMillis()}"))
//      _ <- IO.sleep(1.seconds)
//      _ <- tickingClock
//    } yield ()

//    println(tickingClock.unsafeRunSync())

  //  import cats.effect._
  //  import cats.effect.unsafe.implicits.global
  //  val ohNoes: IO[Int] = IO.raiseError(new RuntimeException("oops"))
  //  val ohNoesWrong = IO(new RuntimeException("oops"))
  //  ohNoes.unsafeRunSync()

//  import cats.syntax.all._
//  def myParMapN[A,B,C](ia: IO[A], ib: IO[B])(f:(A,B) => C): IO[C] =
//    for {
//      fiberA <- ia.start
//      fiberB <- ib.start
//      a <- fiberA.join
//      b <- fiberB.join
//    } yield f(a,b)


//  def joined: IO[String] = {
//    for{
//      fiber <- IO("task").start
//      s <- fiber.join
//    } yield s
//  }

  implicit class Debugger[A](io: IO[A]){
    def debug: IO[A] =
      for{
        ia <- io
        tn = Thread.currentThread().getName
        _  = println(s"[$tn] $ia")
      } yield ia
  }

//  override def run(args: List[String]): IO[ExitCode] =
//    for{
//      _ <- task.start
//      _ <- IO("task started").debug
//    } yield ExitCode.Success

  override def run(args: List[String]): IO[ExitCode] =
    for{
      fiber <- task.start
      _ <- IO("pre-join").debug
      _ <- fiber.join.debug
      _ <- IO("post-join").debug
    } yield ExitCode.Success

  def task: IO[String] = IO.sleep(2.seconds) *> IO("task").debug
}
