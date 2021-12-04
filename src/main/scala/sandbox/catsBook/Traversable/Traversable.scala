package sandbox.catsBook.Traversable

object TraversablePractice extends App{

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

val hostnames =
Option("alpha.example.com")
  List(
  "alpha.example.com",
  "beta.example.com",
  "gamma.demo.com"
)

def getUptime(hostname: String): Future[Int] =
  Future(hostname.length * 60)

// I can't map over because it will give us many futures

//import cats.Traverse
//println(Traverse[Option].sequence(Traverse[Option].traverse(hostnames)(getUptime)))
//  oldCombine(Future[List[Int]], hostnames)

def oldCombine(accum: Future[List[Int]], host: String): Future[List[Int]] = {
  val time = getUptime(host)
  for {
    ac <- accum
    r <- time
  } yield r :: ac
}


  import cats.Applicative
  import cats.syntax.applicative._
  import cats.syntax.apply._
  def listTraverse[F[_]: Applicative, A, B](l: List[A])(f: A => F[B]): F[List[B]] =
    l.foldRight(List.empty[B].pure[F])( (a,b) =>
      (f(a), b).mapN(_ +: _)
    )

  def listSequence[F[_]: Applicative, A](l: List[F[A]]): F[List[A]] =
    listTraverse(l)(identity)

  println(listTraverse(List(1,2,3))(a => Vector(a.toString)))

  println(listSequence(List(Vector(1,2,3), Vector(4,5,6))))
  println(listSequence(List(Vector(1,2), Vector(3,4), Vector(5,6))))

  def process(inputs: List[Int]) =
    listTraverse(inputs)(a => if (a % 2 == 0) Option(a) else None)

  println(process(List(1,2,3)))
  println(process(List(2,4,6)))


  import cats.data.Validated
  import cats.instances.list._

  type ErrorsOr[A] = Validated[List[String], A]
  def process2(input: List[Int]): ErrorsOr[List[Int]] =
    listTraverse(input)( a =>
      if (a % 2 == 0)
        Validated.valid(a)
      else
        Validated.invalid(List("Not odd"))
    )


  println(process2(List(1,2,3)))
  println(process2(List(2,4,6)))
}
