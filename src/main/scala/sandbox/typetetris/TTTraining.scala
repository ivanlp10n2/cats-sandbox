package sandbox.typetetris

import cats.effect._

object TTTraining extends IOApp {
  override def run(args: List[String]): IO[ExitCode] =
    ???

  import scala.concurrent.Future

  sealed trait Request

  object Request {
    final case class Authorized() extends Request

    final case class Unauthorized() extends Request
  }

  sealed trait Response

  object Response {
    final case class Unauthorized() extends Response

    final case class Authorized() extends Response
  }

  cats.instances.string

  def service(request: Request.Unauthorized): Future[Response] = {
    authorize(request) map process getOrElse Future.successful(Response.Unauthorized())
  }

  def process(request: Request.Authorized): Future[Response.Authorized] = ???

  def authorize(request: Request.Unauthorized): Option[Request.Authorized] = ???


}
