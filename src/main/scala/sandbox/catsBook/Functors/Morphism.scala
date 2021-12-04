package sandbox.catsBook.Functors

import cats.effect.{ExitCode, IO, IOApp}

object Morphism extends IOApp{
  override def run(args: List[String]): IO[ExitCode] =IO{
    val f: Int => String = _.toString
    val g: String => Boolean = _.length > 1
    val l: List[Int] = List(1,20,3)

    assert(l.map(f).map(g) == l.map(f andThen g) )
    println(l.map(n => g(f(n))))
//      l.map(n => g(f(n)))
  }.as(ExitCode.Success)

}
