package sandbox.typeclass

import cats.effect._

object TaglessApp extends IOApp {
  override def run(args: List[String]): IO[ExitCode] =
    program.as(ExitCode.Success)

  // Option (a :A)
  // IO (thunk: => A)
  import scala.concurrent.duration._
  def program : IO[Unit] =
    for{
      _ <- IO.println("tick")
      _ <- resourcePrint.use(n =>
        for{
          _ <- IO.sleep(1.seconds)
          _ <-IO(print(s"numero actual $n"))
        } yield ())
      _ <- program
    } yield ()


  def resourcePrint: Resource[IO, Int] =
    Resource.make(IO(
        println("Making resource")).as(123))( _ =>
      IO( println("Releaseing resource")) )



















//  final case class User(name: String)
//
//  trait Users[F[_]] {
//    def getAll: F[List[User]]
//  }
//
//  import cats.ApplicativeError
//
//  type ApThrow[F[_]] = ApplicativeError[F, Throwable]
//
//  def foo[F[_] : Sync, ApThrow, Users]: F[Unit] =
//    F.delay {
//      F.handleError(
//        F.getAll
//      )(_ => "todo mal")
//    }
//
//
//  import scala.util.Try
//  def foo: Try[String]  = ???
//
//  foo.map(s => assert(s == "partner"))
//  foo.fold(t => error)(assert(s == "partner"))
//  foo match {
//    case Failure(_) => assert(false)
//    case Success(s) => assert(true)
//  }



}