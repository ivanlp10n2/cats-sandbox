package sandbox.Applicatives

object SemigroupalApp extends App{

  import cats.Semigroupal
  println(Semigroupal[Option].product(Option(323), None))

  final case class Cat(name: String, age: Int, color: String)

  import cats.syntax.apply._
  val optionableCat = (
    Option("Roberto"),
    Option(18),
    Option("black")
  ).mapN(Cat.apply)
  println(optionableCat)


  import scala.concurrent.Future
  import scala.concurrent.ExecutionContext.Implicits.global
  val futureCat = (
    Future("Jhon"),
    Future(18),
    Future("black")
  ).mapN(Cat.apply)

//  import scala.concurrent.Await
//  import scala.concurrent.duration._
//  assert(Await.result(futureCat, 1.seconds) == optionableCat.get)

  private val listB = List(3, 4)
  private val listA = List(1, 2)
  Semigroupal[List].product(listA, listB)
  (listA, listB).tupled

  println(new Semigroupal[List] {
    override def product[A, B](fa: List[A], fb: List[B]): List[(A, B)] =
      fa.flatMap(a =>
      fb.map( b =>
        (a,b)
      ))
  }.product(listA, listB))

  type err[A]= Either[List[String], A]
  val err1: err[Int] = Left(List("error 1"))

  val err2: err[Int] = Left(List("error 2"))

  import cats.syntax.parallel._
  println((err1, err2).parTupled)

  val addTwo = (x: Int, y: Int) =>
    x + y

  println((err1, err2).parMapN(addTwo))

  import cats.Foldable
  import cats.Eval
  import cats.Monoid
  val bigdata = (0 to 10000).to(List)
  bigdata.foldRight(0L)(_ + _)
  def folding(lazyList: LazyList[Int])(implicit M: Monoid[Int]): Eval[Int] =
    Foldable[LazyList].foldRight(lazyList, Eval.now(M.empty))( (a,b) =>
      Eval.defer( b.map(_ + a) )
    )

  Foldable[List].foldMap(bigdata)(a => a + 3)
}
