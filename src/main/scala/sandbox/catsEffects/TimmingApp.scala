package sandbox.catsEffects

import scala.concurrent.duration.FiniteDuration
import MyIOApp.MyIO

object TimmingApp extends App{
//  import cats.effect.unsafe.implicits.global
def clock: MyIO[Long] = MyIO(() => System.currentTimeMillis())
import scala.concurrent.duration._

  def time[A](action: MyIO[A]): MyIO[(FiniteDuration, A)] =
    clock.flatMap(start =>
      action.flatMap(run =>
        clock.flatMap(end =>
          MyIO(() => ((end - start).millisecond, run)))))

  val timedHello = TimmingApp.time(MyIO.putStr("Hello"))
  println(timedHello.runAsync())
  timedHello.runAsync() match {
    case (duration, _) => println(s"'hello' took $duration")
  }

  import cats.effect._
  val tickingClock =
    for{
      time <- clock
      _ <- MyIO.sleep(1.seconds)
    } yield ()

//  import cats.effect._
//  import cats.effect.unsafe.implicits.global
//  val ohNoes: IO[Int] = IO.raiseError(new RuntimeException("oops"))
//  val ohNoesWrong = IO(new RuntimeException("oops"))
//  ohNoes.unsafeRunSync()
  

}
