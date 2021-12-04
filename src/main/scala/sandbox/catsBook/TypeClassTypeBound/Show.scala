package sandbox.catsBook.TypeClassTypeBound

import cats.Contravariant
import cats.Show.ToShowOps

trait Show[T] extends Show.ContravariantShow[T]

/**
 * Hand rolling the type class boilerplate due to scala/bug#6260 and scala/bug#10458
 */
object Show {

  def apply[A](implicit instance: Show[A]): Show[A] = instance

  trait ContravariantShow[-T] extends Serializable {
    def show(t: T): String
  }

  trait Ops[A] {
    def typeClassInstance: Show[A]
    def self: A
    def show: String = typeClassInstance.show(self)
  }

  trait ToShowOps {
    implicit def toShow[A](target: A)(implicit tc: Show[A]): Ops[A] = new Ops[A] {
      val self = target
      val typeClassInstance = tc
    }
  }

  /** creates an instance of [[Show]] using the provided function */
  def show[A](f: A => String): Show[A] = (a: A) => f(a)

  /** creates an instance of [[Show]] using object toString */
  def fromToString[A]: Show[A] = (a: A) => a.toString

  final case class Shown(override val toString: String) extends AnyVal
  object Shown {
    implicit def mat[A](x: A)(implicit z: ContravariantShow[A]): Shown = Shown(z.show(x))
  }

  final case class ShowInterpolator(_sc: StringContext) extends AnyVal {
    def show(args: Shown*): String = _sc.s(args: _*)
  }

  implicit val catsContravariantForShow: Contravariant[Show] = new Contravariant[Show] {
    def contramap[A, B](fa: Show[A])(f: B => A): Show[B] =
      show[B]((fa.show(_)).compose(f))
  }
}

object S extends App with ToShowOps{
  val n = 50
  val s = "el mundo es plano"

  implicit val showInt: Show[Int] = new Show[Int]{
    override def show(t: Int): String = t.toString
  }
  val showInts = Show.apply[Int]
  import cats.implicits.catsStdShowForInt
  toShow(45).show

  showInts.show(45)
  println(showInts.show(45))

}