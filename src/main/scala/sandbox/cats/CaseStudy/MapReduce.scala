package sandbox.cats.CaseStudy

object MapReduceExercise extends App{
  import cats.syntax.monoid._
  import cats.Monoid

  def foldMap[A,B: Monoid](fa: Vector[A], f: A => B): B =
    fa.foldLeft(Monoid.empty)( (b, a) => b |+| f(a) )
//    fa
//      .map(f)
//      .foldLeft(Monoid.empty)(_ |+| _)

//  println(foldMap(Vector(1,2,3), (n: Monoid=> n.toString)))

  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent._
  import cats.syntax.traverse._
  import cats.syntax.foldable._
  def distributedFoldMap[A,B: Monoid](fa: Vector[A])(f: A => B): Future[B] = {
    val thread = Runtime.getRuntime.availableProcessors
    fa
      .grouped(thread)
      .toVector
      .traverse(e => Future(foldMap(e, f)))
      .map(_.combineAll)
  }

  import scala.concurrent.duration._
  val future: Future[Int] = distributedFoldMap( (0 to 100000).toVector)(_ * 10000)

  println(Await.result(future, 1.seconds))
}
