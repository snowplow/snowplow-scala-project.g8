$copyright$

// sbt
import sbt._
import Keys._

object BuildSettings {
  lazy val projectSettings = Seq(
    organization := "$organization$",
    name := "$name;format="lower,word"$",
    version := "$version$",
    scalaVersion := "$scalaVersion$",
    description := "$projectDescription$"
  )

  // Make package (build) metadata available within source code.
  lazy val scalifiedSettings = Seq(
    sourceGenerators in Compile += Def.task {
      val file = (sourceManaged in Compile).value / "settings.scala"
      IO.write(file, """package $organization$.$name;format="lower,word"$.generated
                       |object ProjectSettings {
                       |  val organization = "%s"
                       |  val name = "%s"
                       |  val version = "%s"
                       |  val scalaVersion = "%s"
                       |  val description = "%s"
                       |}
                       |""".stripMargin.format(organization.value, name.value, version.value, scalaVersion.value, description.value))
      Seq(file)
    }.taskValue
  )

  lazy val compilerSettings = Seq[Setting[_]](
    scalacOptions := Seq(
      "-deprecation",
      "-encoding", "UTF-8",
      "-explaintypes",
      "-feature",
      "-language:existentials",
      "-language:experimental.macros",
      "-language:higherKinds",
      "-language:implicitConversions",
      "-unchecked",
      "-Xcheckinit",
      "-Xfuture",
      "-Yno-adapted-args",
      "-Ypartial-unification",
      "-Ywarn-dead-code",
      "-Ywarn-extra-implicit",
      "-Ywarn-inaccessible",
      "-Ywarn-infer-any",
      "-Ywarn-nullary-override",
      "-Ywarn-nullary-unit",
      "-Ywarn-numeric-widen",
      "-Ywarn-unused",
      "-Ywarn-value-discard"
    ),
    javacOptions := Seq(
      "-source", "1.8",
      "-target", "1.8",
      "-Xlint"
    )
  )

  lazy val helperSettings = Seq[Setting[_]](
    initialCommands := "import $organization$.$name;format="lower,word"$._"
  )

  lazy val resolverSettings = Seq[Setting[_]](
    resolvers ++= Seq(
      "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"
    )
  )
}
