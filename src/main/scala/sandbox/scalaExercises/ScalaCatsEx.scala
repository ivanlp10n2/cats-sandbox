package sandbox.scalaExercises

import cats._
import cats.syntax.all._

object ScalaCatsEx extends App{


  implicit def monoidTuple[A: Monoid, B: Monoid]: Monoid[(A, B)] =
    new Monoid[(A, B)] {
      def combine(x: (A, B), y: (A, B)): (A, B) = {
        val (xa, xb) = x
        val (ya, yb) = y
        (Monoid[A].combine(xa, ya), Monoid[B].combine(xb, yb))
      }

      def empty: (A, B) = (Monoid[A].empty, Monoid[B].empty)
    }

  val l = List(1, 2, 3, 4, 5)
  println(
    l.foldMap(i => (i, i.toString))
  )

  println(cats.Foldable[List]
    .foldK(List(None, Option("two"), Option("three"))) )


}
