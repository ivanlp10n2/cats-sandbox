package sandbox.pfp

import cats.MonadError
import org.http4s.dsl.Http4sDsl

import scala.util.control.NoStackTrace

object ApplicativErrorApp {

  final case class Category()

  trait Categories[F[_]] {
    def findAll: F[List[Category]]
  }

  import cats.ApplicativeError
  import cats.syntax.all._
  type ApThrow[F[_]] = ApplicativeError[F, Throwable]
  class Service[F[_]: ApThrow] (
    categories: Categories[F],
  ) extends Http4sDsl[F]{
      categories.findAll
        .handleError((_: Throwable) => List.empty[Category])
  }

  io.circe.Decoder

  object Mtl {
    sealed trait UserError extends NoStackTrace
    final case class UserAlreadyExists(username: String) extends UserError
    final case class UserNotFound(username: String) extends UserError
    final case class InvalidAge(username: String) extends UserError

    cats.data.Kleisli
    import org.http4s.HttpRoutes
    trait HttpErrorHandler[F[_], E <: Throwable] {
      def handle(routes: HttpRoutes[F]): HttpRoutes[F]
    }

    type Payment = String
    type PaymentId = Int
    type AppError[F[_]] = MonadError[F, Throwable]
    def processPayment[F[_]: AppError](payment:Payment): F[PaymentId] = ???


  }

}
