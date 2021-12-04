package sandbox.catsBook.CaseStudy

//import cats.{Applicative, Id}

import cats.syntax.traverse._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._

object TestAsync {

  /**
  Effect =>
   data structure
   computation

  Effect => F[A]
  type class
  List[Int]
  Future[String]
  Option[Double]

  Option[Int]  match
      Some
      None

  Option(3).map(a => a + 3)  === Some(6) o None
  Future(3).map(a => a + 3) => IO
  val a = 3 + 4

   Option // Abstrae una estructura de datos que tiene o no un valor
   Future // Abstrae una operacion asyncrona
   List   // Contenedor de datos
   // Abstrae una cantidad indefinida de valores, puede ser 0 o n
   NenList // Abstrae una cantidad indefinida de valores, donde n > 0

  List match
    Nil
    a :: Nil
   Set
   Map
   Either
   IO
   Reader
   Writer
  */

  /**
   * Itera a traves de ADT algebraic data types
   * F[A] => fold ( B ) ( (A,B) => B ) => B
   * def fold[B] ( seed: B) ( op: (A, B) => B ) : B = ???
   * List(1,2,3)
   *
   * Aplica una funcion a cada elemento del efecto
   * F[A]
   * def map[B] (f: A => B) : F[B]
   *
   * Aplica una funcion al contenido esperando devolver otro efecto
   * def flatMap[B] (f: A => F[B]): F[B]
   *
   * */
  println(List[Int](1,2,3)
    .foldLeft[String]("inicio: ")( (a,b) => s"$a $b"))

  println(List(1,2,3).map(n => n.toString))

  type ApplicationError = String
  type ApplicationResult[+A] = Future[Either[ApplicationError, A]]



//  def sum(x:Int, y:Int): Option[Int] = ???
//  def sendEmail(n: Int): Option[String] = ???

//  sum(3,4).flatMap(n => sendEmail(n))

  trait UpTimeClient {
    def getUptime(client: String): Future[Int]
  }

  class UpTimeService(client: UpTimeClient) {

//    import cats.syntax.functor._
    def getTotalUptime(hosts: List[String]): Future[Int] =
      hosts
        .traverse(client.getUptime(_))
        .map(_.sum)
  }

//  class RealTestUpdateClient(hosts: Map[String, Int]) extends UpTimeClient[Future] {
//    override def getUptime(client: String): Future[Int] =
//      Future(hosts.get(client).orElse(Option(0)).get)
//  }
//
//  class SyncTestUpdateClient(hosts: Map[String, Int]) extends UpTimeClient[Id] {
//    override def getUptime(client: String): Int =
//      hosts.get(client).orElse(Option(0)).get
//  }

  /**
  val mockedHosts = Map(
    "google" -> 90,
    "amazon" -> 50
  )
  private val client = new SyncTestUpdateClient(mockedHosts)
  val service = new UpTimeService(client)

  val testHostData = List("Google", "amazon")
  assert(service.getTotalUptime(testHostData) == 50)


  object Forest extends App {
    sealed trait Tree
//      def sum: Int = {
//        this match {
//          case Leaf(elt) => elt
//          case Node(l, r) => sum(l) + sum(r)
//        }
//      }
//
//      def double: Tree = {
//        this match {
//          case Leaf(elt) => Leaf(elt * 2)
//          case Node(l, r) => Node(double(l), double(r))
//        }
//      }
//    }

    import cats.syntax.traverse._
    import cats.syntax.parallel._
    List(1,2,3).traverse()
    List(1,2,3).parTraverse()

    final case class Node(left: Tree, right: Tree) extends Tree

    final case class Leaf(elt: Int) extends Tree

    object FuncionesTree {
      def sum(tree: Tree): Int = {
        tree match {
          case Leaf(elt) => elt
          case Node(l, r) => sum(l) + sum(r)
        }
      }
    }
  }



  //    object Cat{
  //      val name: String
  //      Cat(){
  //        this.name = name
  //      }
  //      def getName: String = this.name
  //      def foo(a: String): String =
  //        s"my name is $name $a"
  //    }


  def foo(a: Int): Int =
    a + a
  */

  import cats.effect.IO
//  import scala.concurrent.duration._
//  import cats.effect.syntax._
  import cats.effect._
//  import cats.effect.implicits._
//  import cats.effect.std.Semaphore
  def someExpensiveTask: IO[Int] =
    IO(println("asd")) *> IO(3)
//    IO.sleep(1.second) >> someExpensiveTask
//  import cats.effect.unsafe.implicits.global
//  someExpensiveTask.unsafeRunSync()

//  override def run(args: List[String]): IO[ExitCode] =
//    IO.pure(3).as(ExitCode.Success)
//  private def either: Either[String, Int] = Right(3)

}
