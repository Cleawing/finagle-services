import org.stormenroute.mecha._
import sbt._
import sbt.Keys._

object Build extends MechaRepoBuild {
  lazy val buildSettings = Defaults.coreDefaultSettings ++
    MechaRepoPlugin.defaultSettings ++ Seq(
    name := "finagle-services",
    scalaVersion := "2.11.7",
    version := "0.1",
    organization := "com.cleawing",
    libraryDependencies ++= superRepoDependencies("finagle-services") ++ Dependencies.finagle ++
      Seq(Dependencies.typesafeConfig, Dependencies.bouncyCastleProvider, Dependencies.scalaTest)
  )

  def repoName = "finagle-services"

  lazy val finagleServices: Project = Project(
    "finagle-services",
    file("."),
    settings = buildSettings
  ) dependsOnSuperRepo
}
