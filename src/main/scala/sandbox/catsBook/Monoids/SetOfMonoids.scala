package sandbox.catsBook.Monoids

import sandbox.catsBook.Monoids.MonoidApp.Monoid

object SetOfMonoids extends App{

  object SetMonoidImplicit {
    implicit def setUnionMonoid[A]: Monoid[Set[A]] = new Monoid[Set[A]] {
      override def empty: Set[A] = Set[A]()

      override def combine(x: Set[A], y: Set[A]): Set[A] = x union y
    }
  }
  {
    import SetMonoidImplicit._
    println(Monoid[Set[Int]].combine(Set(1), Set(2)))
    println(Monoid[Set[String]].combine(Set("Que onda"), Set("1")))
    println(Monoid[Set[Set[String]]].combine(Set(Set("asd")), Set(Set("qwe"))))

    cats.Monoid
  }

  {
    import cats.Monoid
    import cats.instances.int._
    import cats.instances.option._
    println(Monoid[Option[Int]].combine(Option(1), Option(2)))

    import cats.syntax.semigroup._
    assert( (Option(1) |+| Monoid[Option[Int]].empty) == ( Monoid[Option[Int]].empty |+| Option(1) ) )
    println( Option(1) |+| Option(2) |+| Monoid[Option[Int]].empty)
  }

  { // 2.5.4 Adding all the things
    import cats.Monoid
    import cats.syntax.semigroup._
    def add[A: Monoid](items: List[A]): A=
      items.fold(Monoid[A].empty) (_ |+| _)

    assert(add(List("a", "b", "c", "d", "e")) == "abcde")
    import cats.instances.option._
    import cats.instances.int._
    assert(add(List(Option(3), Option(4))) == Option(7))

    case class Order(totalCost: Double, quantity: Double)
    implicit val m: Monoid[Order] = new Monoid[Order] {
      override def empty: Order = Order(0,0)

      override def combine(x: Order, y: Order): Order =
        Order(x.totalCost |+| y.totalCost,
          x.quantity |+| y.quantity)
    }

    assert(add(List(Order(2,3), Order(3,2))) == Order(5, 5))
  }
  { // CASE STUDY
    /**
     * In big data applications like Spark and Hadoop we distribute
     * data analysis over many machines, giving fault tolerance and scalability.
     * This means each machine will return results over a portion of the data, and
     * we must then combine these results to get our final result. In the vast majority
     * of cases this can be viewed as a monoid.
     *
     * If we want to calculate how many total visitors a web site has received,
     * that means calculating an Int on each portion of the data. We know the monoid
     * instance of Int is addition, which is the right way to combine partial results.
     *
     * If we want to find out how many unique visitors a website has received, that’s
     * equivalent to building a Set[User] on each portion of the data. We know the monoid
     * instance for Set is the set union, which is the right way to combine partial results.
     *
     * If we want to calculate 99% and 95% response times from our server logs, we can use
     * a data structure called a QTree for which there is a monoid.
     *
     * Hopefully you get the idea. Almost every analysis that we might want to do over a
     * large data set is a monoid, and therefore we can build an expressive and powerful
     * analytics system around this idea
     */

  }
}
