package haskell

import cats.effect.{ExitCode, IO, IOApp}

object HaskellTryApp extends IOApp{
  override def run(args: List[String]): IO[ExitCode] = IO{
    println(2 + 2)
  }.as(ExitCode.Success)

  def showInt: Int => String = _.toString
  def floorInt: Double => Int = Math.floor(_).toInt
  def maybeInt: Int => Option[Int] = Some(_)

  type MaybeString[A] = {
    def makeString: A => String
  }

  val a :Double => Option[Int] = maybeInt compose floorInt

}
