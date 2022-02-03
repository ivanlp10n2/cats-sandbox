package sandbox.catsBook.Functors

object Contravariant extends App{
  import cats.Show
  val showString: Show[String] = Show[String]

  val showSymbol: Show[Symbol] = cats.Contravariant[Show]
    .contramap[String, Symbol](showString)(s => s"Symbol name: ${s.name}")

  import cats._
  println(showString.show("que onda"))
  println(showSymbol.show(Symbol("tuvieja")))

  import cats.syntax.contravariant._
  showString
    .contramap[Symbol](s => s"Symbol name: ${s.name}")
    .show(Symbol("tuvieja"))

}
