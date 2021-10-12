package sandbox.cats.Monads

object EitherApp extends App{

  import cats.syntax.either._
  (for{
    a <- 1.asRight[String]
    b <- 2.asRight[Int]
  } yield (a - b) )
    .map (println _)
}
