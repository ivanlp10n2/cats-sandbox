package sandbox.catsBook.CaseStudy

object CRDTs extends App{

  final case class GCounter(counters: Map[String, Int]){
    import cats.syntax.semigroup._
    def increment(machine: String, amount: Int) =
      counters.get(machine).map(n  => n |+| amount)

//    def merge(that: GCounter): GCounter =
//      that.

    def total: Int = counters.values.sum
  }

}
