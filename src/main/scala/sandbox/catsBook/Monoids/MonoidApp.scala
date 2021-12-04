package sandbox.catsBook.Monoids

object MonoidApp extends App{

  trait Semigroup[A] {
    def combine(x: A, y:A): A
  }

  trait Monoid[A] extends Semigroup[A]{
    def empty: A
  }

  object Monoid{
    def apply[A](implicit monoid: Monoid[A]): Monoid[A] = monoid
  }

  object ImplicitsMonoids{
    implicit val combineBooleanAnd: Monoid[Boolean] =
      new Monoid[Boolean] {
        override def empty: Boolean = Boolean.box(true)

        override def combine(x: Boolean, y: Boolean): Boolean =
          x && y
      }

    implicit val combineBooleanOr: Monoid[Boolean] =
      new Monoid[Boolean] {
        override def empty: Boolean = Boolean.box(false)

        override def combine(x: Boolean, y: Boolean): Boolean =
          x || y
      }

    implicit val combineBooleanXor: Monoid[Boolean] =
      new Monoid[Boolean] {
        override def empty: Boolean = Boolean.box(true)

        override def combine(x: Boolean, y: Boolean): Boolean =
          x || y
      }
  }

  { // combine with And
    import ImplicitsMonoids.combineBooleanAnd
    assert(Monoid[Boolean].combine(true, true))
  }

  { // combine with Or
    import ImplicitsMonoids.combineBooleanOr
    assert(Monoid[Boolean].combine(true, false))
  }
  {}//TODO: Should combine with Xor and Nor

}

