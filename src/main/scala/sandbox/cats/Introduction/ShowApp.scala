package sandbox.cats.Introduction

import sandbox.cats.Introduction.PrintableCat.Cat

import java.util.Date


object ShowApp extends App{
  import cats.Show
  import cats.syntax.show._

  {
    import cats.implicits.catsSyntaxOptionId
    println("asd".some.show)
  }

  implicit val showDate = Show.show[Date](d => s"time is $d")
  println( showDate.show(new Date()) )

  implicit val showCat = Show.show[Cat](c => s"${c.name.show} is a ${c.age.show} year-old ${c.color.show} cat.")
  println(Cat("Jorge", 23, "pink").show)

}
