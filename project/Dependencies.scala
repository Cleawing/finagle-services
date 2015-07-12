import sbt._

object Dependencies {
  object Versions {
    val typesafeConfig = "1.3.0"
    val finagle = "6.26.0"
    val bouncyCastleProvider = "1.46"
    val scalaz = "7.1.3"
    val json4s = "3.3.0.RC2"
    val jackson = "2.6.0-rc3"
    val scalazScalaTest = "0.2.3"
    val scalaTest = "2.2.5"
  }

  lazy val typesafeConfig  = "com.typesafe" % "config" % Versions.typesafeConfig

  lazy val finagle = Seq(
    "com.twitter" %% "finagle-httpx" % Versions.finagle
  )

  lazy val scalaz  = "org.scalaz" %% "scalaz-core" % Versions.scalaz
  lazy val json4s = "org.json4s" %% "json4s-jackson" % Versions.json4s
  lazy val jackson = "com.fasterxml.jackson.core" % "jackson-databind" % Versions.jackson
  lazy val bouncyCastleProvider = "org.bouncycastle" % "bcprov-jdk16" % Versions.bouncyCastleProvider

  lazy val scalazScalaTest  = "org.typelevel" %% "scalaz-scalatest" % Versions.scalazScalaTest % "test"
  lazy val scalaTest  = "org.scalatest" %%  "scalatest" % Versions.scalaTest % "test"
}
