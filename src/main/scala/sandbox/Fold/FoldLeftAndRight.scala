package sandbox.Fold

object FoldLeftAndRight extends App {

  sealed trait List[+A]{
    final def foldLeft[B](seed: B)(f: (B, A) => B): B =
      this match {
        case Node(head, tail) => tail.foldLeft(f(seed, head))(f)
        case Nil => seed
      }

    final def foldRight[B](seed: B)(f: (A, B) => B): B =
      this match {
        case Node(head, tail) => f(head, tail.foldRight(seed)(f))
        case Nil => seed
      }
  }
  final case class Node[A](head:A, tail: List[A]) extends List[A]
  final case object Nil extends List[Nothing]


}
