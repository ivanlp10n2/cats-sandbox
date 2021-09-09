Type class is a way to model polymorphic behaviour ad-hoc:
To model polymorphic behaviour without change source code
To model polymorphic behaviour without using inheritance

type class are constituted by:

- parametrized Trait[T] that has functionality we want to add
in the parameter we want to work.
aka: type class

- Implicit value that implements the type class. 
aka: type class instance

- Object to save implicit value (so you import when needed)
aka: type usage

- (optionally) we can build syntactic sugar for the usage
by building implicit classes and calling type class methods.
aka: type class syntax







Type compatibility: 
In Java we say two types are compatible because 
it's possible to transfer data between variables of the types.

Data transfer is possible if the compiler accepts it, and
it can be done through assigment or parameter passing.
As an example: Short is compatible with Int because :
intValue = shortValue is possible. 

But Boolean is not compatible with Int because the assigment :
intValue= booleanValue is not possible.

Because compatibility is a direct relation, sometimes T1 is
compatible to T2 but T2 is not compatible with T1.

In Java compatibility is possible only within a type hierarchy

Covariance: "different in the same direction" - with-different
Contravariance: "different in the opposite direction" - against-different

A type A is covariant to B if from A you can get a B as: A => B
A type A is contravariant to B if from A you can get a B as: B => A

A language element (type or method) A[T] depending on T:
  is *covariant* if the compatibility of T1 to T2 implies 
  the compatibility of a A[T1] to A[T2]. 

  is *contravariant* if the compatibility of T1 to T2 implies
  the compatibility of A[T2] to A[T1]

  is *invariant* if the compatibility of T1 and t2 doesn't imply
  any compatibility between A[T1] and A[T2] then A[T] is invariant

```scala
/**
 *Liskov Substitution Principle: 
 * everything that's true about a superclass 
 * should be true of all its subclasses. You 
 * should be able to do with a SubFoo everything 
 * that you can do with a Foo, and maybe more.
 */
```
suppose we have Calico <: Cat <: Animal and Husky <: Dog <: Animal
```scala
trait Animal{ val name: String }
class Cat(name:String) extends Animal
class Calico(numberOfLegs: Int) extends Cat

class Dog(name: String) extends Animal
class Husky(timesOfHowlPerDay: Int) extends Dog

def doCat(c: Cat): Cat = ???
val a: Calico = doCat(Calico())

def list[T](l: List[T]): List[T] = ???
val b: Calico = list[Cat](Cat("Jorge"))


```
