package sandbox.CaseStudy

import cats.{Applicative, Id}

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import cats.syntax.traverse._

object TestAsync extends App{

  trait UpTimeClient[F[_]] {
    def getUptime(client: String): F[Int]
  }

  class UpTimeService[F[_]: Applicative](client: UpTimeClient[F]) {
    import cats.syntax.functor._
    def getTotalUptime(hosts: List[String]): F[Int] =
      hosts
        .traverse(client.getUptime(_))
        .map(_.sum)
  }

  class RealTestUpdateClient(hosts: Map[String, Int]) extends UpTimeClient[Future]{
    override def getUptime(client: String): Future[Int] =
      Future(hosts.get(client).orElse(Option(0)).get)
  }

  class SyncTestUpdateClient(hosts: Map[String, Int]) extends UpTimeClient[Id]{
    override def getUptime(client: String): Int =
      hosts.get(client).orElse(Option(0)).get
  }

  val mockedHosts = Map(
    "google" -> 90,
    "amazon" -> 50
  )
  private val client = new SyncTestUpdateClient(mockedHosts)
  val service = new UpTimeService(client)

  val testHostData = List("Google", "amazon")
  assert (service.getTotalUptime(testHostData) == 50)

}
