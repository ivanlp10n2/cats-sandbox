package sandbox.catsBook.TypeClassTypeBound

import sandbox.catsBook.TypeClassTypeBound.TypeClassesSandbox.EqualsSandbox.Equal
import sandbox.catsBook.TypeClassTypeBound.TypeClassesSandbox.EqualsSandbox.Equal.ToEqual

object TypeClassesSandbox extends App {

  object EqualsSandbox {
    trait Equal[A] {
      def equal(v1: A, v2: A): Boolean
    }

    object Equal {
      def apply[A](implicit instance: Equal[A]): Equal[A] =
        instance

      implicit class ToEqual[A](v1: A) {
        def ===(v2: A)(implicit equal: Equal[A]): Boolean = equal.equal(v1, v2)
      }
    }
  }

  object StringCaseInsensitiveImplicits {
    implicit val caseInsensitiveEquals = new Equal[String] {
      override def equal(v1: String, v2: String): Boolean = v1.toLowerCase == v2.toLowerCase
    }
  }

  import StringCaseInsensitiveImplicits._
  assert("hello" === "HELLO")

  /**
   * map      - Functor
   * filter   - Filterable
   * flatMap  - Monad
   * zip      - Applicative
   * fold     - Traversable
   */
}
