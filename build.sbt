name := "cats-sandbox"
version := "0.0.1-SNAPSHOT"

scalaVersion := "2.13.1"

libraryDependencies += "org.typelevel" %% "cats-core" % "2.7.0"
libraryDependencies += "io.estatico" %% "newtype" % "0.4.4"
libraryDependencies ++= Seq(
  "eu.timepit" %% "refined"                 % "0.9.27",
  "eu.timepit" %% "refined-cats"            % "0.9.27", // optional
  "eu.timepit" %% "refined-eval"            % "0.9.27", // optional, JVM-only
  "eu.timepit" %% "refined-jsonpath"        % "0.9.27", // optional, JVM-only
  "eu.timepit" %% "refined-pureconfig"      % "0.9.27", // optional, JVM-only
  "eu.timepit" %% "refined-scalacheck"      % "0.9.27", // optional
  "eu.timepit" %% "refined-scalaz"          % "0.9.27", // optional
  "eu.timepit" %% "refined-scodec"          % "0.9.27", // optional
  "eu.timepit" %% "refined-scopt"           % "0.9.27", // optional
  "eu.timepit" %% "refined-shapeless"       % "0.9.27"  // optional
)
val http4sVersion = "0.23.1"
libraryDependencies += "org.typelevel" %% "cats-effect" % "3.2.9"
libraryDependencies += "org.http4s" %% "http4s-dsl" % http4sVersion
libraryDependencies += "org.http4s" %% "http4s-ember-client" % http4sVersion
libraryDependencies += "org.http4s" %% "http4s-ember-server" % http4sVersion
//libraryDependencies += "org.http4s" %% "http4s" % http4sVersion
val circeVersion = "0.14.1"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.10"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)
// scalac options come from the sbt-tpolecat plugin so need to set any here

libraryDependencies += "org.scalameta" %% "munit" % "0.7.29"
testFrameworks += new TestFramework("munit.Framework")

addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.13.2" cross CrossVersion.full)
addCompilerPlugin("org.augustjune" %% "context-applied" % "0.1.4")
scalacOptions ++= Seq("-Ymacro-annotations")