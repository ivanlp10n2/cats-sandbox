package sandbox.cats.TypeClassTypeBound

object JsonSerialization extends App {

  sealed trait JsValue {
    def stringify: String
  }

  final case class JsObject(values: Map[String, JsValue]) extends JsValue {
    override def stringify: String = values
      .map { case (name, value) => "\"" + name + "\":" + value.stringify }
      .mkString("{", ",", "}")
  }

  final case class JsString(value: String) extends JsValue {
    override def stringify: String =
      "\"" +
        value.replaceAll("\\|\"", "\\\\$1") +
        "\""
  }

  import java.util.Date

  sealed trait Visitor {
    def id: String

    def createdAt: Date

    def age: Long = new Date().getTime() - createdAt.getTime()
  }

  final case class Anonymous(
                              id: String,
                              createdAt: Date = new Date()
                            ) extends Visitor

  final case class User(id: String,
                        email: String,
                        createdAt: Date = new Date()
                       ) extends Visitor

  //Type class
  trait JsWriter[A] {
    def write(value: A): JsValue
  }

  //Type class use
  object JsUtil {
    def toJson[A](value: A)(implicit instance: JsWriter[A]): JsValue =
      instance write value
  }

  //Type class use interface syntax
  object JsUtilSyntax {
    implicit class JsUtilOps[A](value: A) {
      def toJson(implicit instance: JsWriter[A]): JsValue =
        instance write value
    }
  }

  //type class instances
  object Implicits {
    implicit val anonymousWriter = new JsWriter[Anonymous] {
      override def write(value: Anonymous): JsValue =
        JsObject(
          Map(
            "id" -> JsString(value.id),
            "createdAt" -> JsString(value.createdAt.toString)
          ))
    }

    implicit val userWriter = new JsWriter[User] {
      override def write(value: User): JsValue =
        JsObject(
          Map(
            "id" -> JsString(value.id),
            "email" -> JsString(value.email),
            "createdAt" -> JsString(value.createdAt.toString),

          ))
    }
  }

  //type class instance of Visitor
  implicit object VisitorWriter extends JsWriter[Visitor] {

    import Implicits._

    override def write(value: Visitor): JsValue = value match {
      case an: Anonymous => JsUtil.toJson(an)
      case us: User => JsUtil.toJson(us)
    }
  }

  val visitors: Seq[Visitor] = Seq(
    Anonymous("001", new Date),
    User("003", "dave@xample.com", new Date)
  )

  import JsUtilSyntax._

  println(s" STarting ${visitors.map(visitor => visitor.toJson.stringify)}")
}
