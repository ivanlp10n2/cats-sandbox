package sandbox.cats.Monads

object ReaderApp extends App{
  case class Cat(name: String)

  import cats.data.Reader
  val op = Reader[Cat, String](cat => cat.name)


  final case class Db(
     username: Map[Int, String],
     password: Map[String, String]
   )

  type DbReader[A] = Reader[Db, A]

  def findUsername(userId: Int): DbReader[Option[String]] =
    Reader(db =>
      db.username.get(userId))

  def findPassword(username: String, password: String): DbReader[Boolean] =
    Reader(db =>
      db.password.get(username)
        .contains(password))

  import cats.syntax.applicative._
  def checkLogin(userId: Int, password: String): DbReader[Boolean] =
    for{
      username <- findUsername(userId)
      password <- username
        .map( user => findPassword(user, password) )
        .getOrElse( false.pure[DbReader])
    } yield password

  val usernames = Map (
    1 -> "Jhon",
    2 -> "Jorge"
  )
  val passwords = Map (
    "Jhon" -> "Salchichon",
    "Jorge"-> "Curioso"
  )
  assert (checkLogin(1, "Salchichon")
    .run(Db(usernames, passwords))
  )
}
