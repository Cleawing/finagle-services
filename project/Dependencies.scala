import sbt._

object Dependencies {
  object Versions {
    val finch = "0.7.1",
    val bouncyCastleProvider = "1.46",
    val scalaTest = "2.2.5"
  }

  lazy val finch = Seq(
    "com.github.finagle"  %%  "finch-core"  % Versions.finch,
    "com.github.finagle"  %%  "finch-json4s"  % Versions.finch,
  )

  lazy val bouncyCastleProvider = "org.bouncycastle" % "bcprov-jdk16" % Versions.bouncyCastleProvider

  lazy val scalaTest  = "org.scalatest" %%  "scalatest" % Versions.scalaTest
}
