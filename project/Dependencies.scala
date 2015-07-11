import sbt._

object Dependencies {
  object Versions {
    val typesafeConfig = "1.3.0"
    val finagle = "6.26.0"
    val bouncyCastleProvider = "1.46"
    val scalaTest = "2.2.5"
  }

  lazy val typesafeConfig  = "com.typesafe" % "config" % Versions.typesafeConfig

  lazy val finagle = Seq(
    "com.twitter" %% "finagle-httpx" % Versions.finagle
  )

  lazy val bouncyCastleProvider = "org.bouncycastle" % "bcprov-jdk16" % Versions.bouncyCastleProvider

  lazy val scalaTest  = "org.scalatest" %%  "scalatest" % Versions.scalaTest % "test"
}
