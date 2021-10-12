package sandbox.cats.TypeClassTypeBound

object Main extends App {

  import eu.timepit.refined.api.Refined
  import eu.timepit.refined.auto._
  import eu.timepit.refined.string.MatchesRegex

  type regexEmail = "^[a-zA-Z0-9.]+@[a-zA-Z0-9]+\\.[a-zA-Z]+$"
  type Email = String Refined MatchesRegex[regexEmail]

  //  val us: Email = "a@"
  val ea: Email = "a@a.com"
}
