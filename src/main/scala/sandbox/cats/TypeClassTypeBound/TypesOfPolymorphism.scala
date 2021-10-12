package sandbox.cats.TypeClassTypeBound

object TypesOfPolymorphism {
  /**
   * There are three types of polymorphism:
   * Subtype Polymorphism
   * Parametric Polymorphism
   * Ad-Hoc Polymorphism
   */

  object Inheritance {
    trait Animal {
      def doSound: String
    }

    final case class Dog(name: String) extends Animal {
      override def doSound: String = s"My name is $name, guau!"
    }

    final case class Cat(name: String) extends Animal {
      override def doSound: String = s"My name is $name, meow!"
    }

    val julio = Dog("Julio")
    val carla = Cat("Carla")

    def animalDoesSound(animal: Animal): String = animal.doSound

    animalDoesSound(julio)
  }

  object Generics {
    trait DoSound[Animal] {
      def doSound: String
    }

  }

  object TypeClass {

  }
}
