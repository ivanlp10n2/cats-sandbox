package sandbox.catsBook.TypeClassTypeBound

object TypeClassOverPatternMatching extends App{

  {
    case class Cat(name: String)
    case class Dog(name: String)

    //Need to define new behaviour for both: Talk
//    type CatOrDog = Any // Something that relates them
//    def talk(catOrDog: CatOrDog): String = ???

//    assert(talk(Dog("Julio"))  == s"My name is Julio guau!")
//    assert(talk(Cat("Martina"))== s"My name is Martina miau!")
  }

  { // extends Source Code
    sealed trait Animal{
      val name: String
      def talk: String
    }
    case class Cat(name: String) extends Animal {
      override def talk: String = s"My name is $name miau!"
    }
    case class Dog(name: String) extends Animal {
      override def talk: String = s"My name is $name guau!"
    }

    def talk(catOrDog: Animal): String =
      catOrDog.talk

    assert(talk(Dog("Julio"))  == s"My name is Julio guau!")
    assert(talk(Cat("Martina"))== s"My name is Martina miau!")
  }

  { // multiple dispatching not possible

//    case class Cat(name: String)
//    case class Dog(name: String)

//    def talk(dog: Dog): String =
//      s"My name is ${dog.name} guau!"

//    def talk(cat: Cat): String =
//      s"My name is ${cat.name} miau!"

//    assert(talk(Dog("Julio"))  == s"My name is Julio guau!")
//    assert(talk(Cat("Martina"))== s"My name is Martina miau!")
  }


  { // dynamic dispatching (using type parameters)
    abstract class Animal(){
      val name: String
      def talk: String
    }

    case class Cat(name: String) extends Animal {
      override def talk: String = s"My name is $name miau!"
    }
    case class Dog(name: String) extends Animal {
      override def talk: String = s"My name is $name guau!"
    }

    def talk[T <: Animal](animal : T): String =
      animal.talk

    assert(talk(Dog("Julio"))  == s"My name is Julio guau!")
    assert(talk(Cat("Martina"))== s"My name is Martina miau!")
  }


  { // define only its relationship and implement on function
    sealed trait Animal
    case class Cat(name: String) extends Animal
    case class Dog(name: String) extends Animal

    def talk(catOrDog: Animal): String = // Can also be done using multiple dispatching
      catOrDog match {
        case Cat(name) => s"My name is $name miau!"
        case Dog(name) => s"My name is $name guau!"
      }

    assert(talk(Dog("Julio")) == s"My name is Julio guau!")
    assert(talk(Cat("Martina")) == s"My name is Martina miau!")
  }

  { // define compound types relationship and implement generically
    // we lose exhaustiveness (compile checking) at pattern matching
    // Maybe with union types -scala3- we can get exhaustiveness back
    case class Cat(name: String)
    case class Dog(name: String)

    def talk[A](catOrDog: A)(implicit ev: (Cat with Dog) <:< A): String =
      catOrDog match {
        case Cat(name) => s"My name is $name miau!"
        case Dog(name) => s"My name is $name guau!"
        case "whatever" => s"This"
      }

    assert(talk(Dog("Julio")) == s"My name is Julio guau!")
    assert(talk(Cat("Martina")) == s"My name is Martina miau!")
    // talk("whatever") won't compile
  }

  { // Typeclass
    case class Cat(name: String)
    case class Dog(name: String)

    sealed trait Talk[A]{
      def apply(catOrDog: A): String
    }
    object Talk{
      implicit val dogTalk : Talk[Dog] = new Talk[Dog] {
        override def apply(catOrDog:  Dog): String =
          s"My name is ${catOrDog.name} guau!"
      }
      implicit val catTalk : Talk[Cat] = new Talk[Cat] {
        override def apply(catOrDog:  Cat): String =
          s"My name is ${catOrDog.name} miau!"
      }
    }

    def talk[A](catOrDog: A)(implicit talk: Talk[A]): String =
      talk(catOrDog)

    assert(talk(Dog("Julio")) == s"My name is Julio guau!")
    assert(talk(Cat("Martina")) == s"My name is Martina miau!")
  }
}
