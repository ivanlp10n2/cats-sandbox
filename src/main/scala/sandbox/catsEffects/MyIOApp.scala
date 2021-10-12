package sandbox.catsEffects

object MyIOApp extends App {

  object MyIO {
    def putStr(s: => String): MyIO[Unit] =
      MyIO(() => println(s))
  }

  final case class MyIO[A](runAsync: () => A) {

    def map[B](f: A => B): MyIO[B] = {
      val torun: () => B = () => f(runAsync())
      MyIO(torun)
    }

    def flatMap[B](f: A => MyIO[B]): MyIO[B] =
      MyIO(f(runAsync()).runAsync)
  }

  MyIO.putStr("Something")
    .flatMap(_ => MyIO.putStr("vieja"))
    .runAsync()

//  (for{
//    _ <- MyMIO.putStr("hello")
//    b <- MyMIO.putStr("world")
//  } yield a + b)
//    .runAsync
//  MyMIO.putStr("tuvieja")
val hello = MyIO.putStr("hello!")
val world = MyIO.putStr("world!")
val helloWorld: MyIO[Unit] =
  for {
    _ <- hello
    _ <- world
  } yield ()
helloWorld.runAsync()
}
