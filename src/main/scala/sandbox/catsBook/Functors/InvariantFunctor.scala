package sandbox.catsBook.Functors

object InvariantFunctor extends App{
  trait Codec[A] { self =>
    def encode(value: A): String
    def decode(value: String): A
    def imap[B](dec: A => B, enc: B => A): Codec[B] =
      new Codec[B] {
        override def encode(value: B): String = self.encode(enc(value))

        override def decode(value: String): B = dec(self.decode(value))
      }
  }

  def encode[A](value: A)(implicit c: Codec[A]): String =
    c.encode(value)

  def decode[A](value: String)(implicit c: Codec[A]): A =
    c.decode(value)

  implicit val stringCodec: Codec[String]= new Codec[String]{
    override def decode(value: String): String = value

    override def encode(value: String): String = value
  }

  implicit val intCodec: Codec[Int] =
    stringCodec.imap[Int](_.toInt, _.toString)

  implicit val booleanCodec: Codec[Boolean] =
    stringCodec.imap[Boolean](_.toBoolean, _.toString)

  implicit val doubleCodec: Codec[Double] =
    stringCodec.imap[Double](_.toDouble, _.toString)

  final case class Box[A](value: A)
  implicit def boxCodec[A](implicit C: Codec[A]): Codec[Box[A]] =
    C.imap[Box[A]](a => Box(a), box => box.value)


  assert( encode(123.4) == "123.4")
  // res11: String = "123.4"
  assert( decode[Double]("123.4") == 123.4)
  // res12: Double = 123.4

  assert( encode(Box(123.4)) == "123.4")
  // res13: String = "123.4"
  assert( decode[Box[Double]]("123.4") == Box(123.4))
  // res14: Box[Double] = Box(123.4)

  type Id[A] = A
  import cats.syntax.all._
  import cats._
//  List(1,2,3).traverse(identity)

//  identity[List, Int, String]

//  def identity[F[_]: Applicative, A, B]: A => F[B] = {
//    a => a.pure[F]
//  }

  def listTraverse[F[_]: Applicative, A, B]
      (list: List[A])(func: A => F[B]): F[List[B]] =
    list.foldLeft(List.empty[B].pure[F]) { (accum, item) =>
      (accum, func(item)).mapN(_ :+ _)
  }

  def listSequence[F[_]: Applicative, B]
        (list: List[F[B]]): F[List[B]] =
    listTraverse(list)(identity)

//    F[A] => (A => G[B]) => G[F[B]]
    // F[G[B]] => (B => B) => G[F[B]] // == sequence
    import cats.instances.all._
    def foo[F[_]: Applicative,A,B]: Option[List[Int]] =
      List[Option[Int]](Some(1),Some(2),Some(3))
        .traverse(identity)
}
