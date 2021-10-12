package sandbox.cats.Introduction

import cats.Eq


object EqApp extends App{
  {
    import cats.implicits.catsSyntaxEq
    import cats.instances.option._
    import cats.instances.int._
    val l = List(1, 2, 3).map(Option(_)).filter(_ === Option(3))
    println(l)
    (Some(1): Option[Int]) === (None: Option[Int])
  }

  {
    import cats.Eq
    import cats.instances.int._
    val eqInt = Eq[Int]
    eqInt.neqv(3,3)
  }

  {
    final case class Cat(name: String, age: Int, color: String)
    val cat1 = Cat("Jorge", 38, "orange")
//    val cat2 = Cat("Junior", 98, "white")

    val optionCat1 = Option(cat1)
    val optionCat2 = Option.empty[Cat]

    implicit val eq ={
        import cats.syntax.eq._
        import cats.instances.int._
        import cats.instances.string._
        Eq.instance[Cat] { (cat1, cat2) =>
          (cat1.name === cat2.name) &&
            (cat1.age === cat2.age) &&
            (cat1.color === cat2.color)
        }
    }
    val eqOpt = Eq[Option[Cat]]
    println(eqOpt.eqv(optionCat1, optionCat2))
    println(eqOpt.neqv(optionCat1, optionCat2))
    println(eqOpt.eqv(optionCat1, optionCat1))

  }


}
