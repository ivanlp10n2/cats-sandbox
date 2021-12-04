package sandbox.pfp

import cats.MonadError
import org.http4s.dsl.Http4sDsl
import scalaz.Kleisli
import cats.effect._
import cats._

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
    trait HttpErrorHandler[F[_], E <: Throwable] {
      def handle(routes: HttpRoutes[F]): HttpRoutes[F]
    }

    type Payment = String
    type PaymentId = Int
    type AppError[F[_]] = MonadError[F, Throwable]
//    def processPayment[F[_]: AppError](payment:Payment): F[PaymentId] = ???

    type Request[F[_]] = F[Any]
    case class Response[F[_]]()

    import cats.data.OptionT
    case class AuthedRequest[F[_], User](user: User, request: Request[F])
    type HttpRoutes[F[_]] = Kleisli[OptionT[F, *], Request[F], Response[F]]

    final case class LiveUser()
    def authedRouted(req: AuthedRequest[IO, LiveUser]): AuthedRoutes[LiveUser, IO] = ???

    type AuthedRoutes[User, F[_]] = Kleisli[OptionT[F, *], AuthedRequest[F, User], Response[F]]

    type Foo[F[_], T] = AuthedRoutes[T, F] => HttpRoutes[F]
    type AuthR[F[_]] = AuthedRequest[F, LiveUser]
//    def valFoo: Foo[IO, LiveUser] = Kleisli{ a : Request[LiveUser] => IO(ExitCode.Success)}



    type Middleware[F[_]] = Kleisli[F, Request[F], Response[F]] => Kleisli[F, Request[F], Response[F]]
    type FooFighter[F[_]] = Request[F] => F[Response[F]] => Request[F] => F[Response[F]]

    def foo: HttpRoutes[IO] = ???

    def applicative[A[_]: Applicative, S, T]: A[S => T] => A[S] => A[T] = Applicative[A].ap
    cats.Alternative

  }
}
