package sandbox.TypeClassTypeBound

object CatsBeginning extends App {

  import cats.Show
  import cats.instances.int._
  import cats.syntax.show._

  val showInt = Show.apply[Int]
  println(showInt.show(3))
  println(3.show)
}
