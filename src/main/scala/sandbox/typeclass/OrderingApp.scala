package sandbox.typeclass

import cats.effect.{ExitCode, IO, IOApp}
object OrderingApp extends IOApp{
  override def run(args: List[String]): IO[ExitCode] =
    IO(print("asdad"))
    .as(ExitCode.Success)


  trait Ladrable{
    def ladrar: String
  }
  final case class Dog()
  object Dog{ // Instance
    implicit val dogShow: Show[Dog] = new Show[Dog] {
      override def ladrar(t: Dog): String = s"$t asda"
    }
  }
  trait Show[T]{ // Type class
    def ladrar(t:T): String
  }

  dogAlgo(Dog())

  def dogAlgo(dog: Dog)(implicit S: Show[Dog]) = S.ladrar(dog) // Interface

  implicit class DogSyntax(d: Dog){
    def ladrar(implicit S: Show[Dog]) = S.ladrar(d)
  }

  Dog().ladrar

  // Compile time -> types
  // Runtime -> value

}