ThisBuild / organization := "com.timushev"

val versions = new {
  val scala212 = "2.12.12"
  val scala213 = "2.13.4"
  val zio      = "1.0.3"
}

lazy val `zio-test-macros` = projectMatrix
  .in(file("lib"))
  .settings(
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect"  % scalaVersion.value % Provided,
      "org.scala-lang" % "scala-compiler" % scalaVersion.value % Provided,
      "dev.zio"      %%% "zio-test"       % versions.zio,
      "dev.zio"      %%% "zio-test-sbt"   % versions.zio       % Test
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework"),
    publishTo := sonatypePublishToBundle.value
  )
  .jvmPlatform(Seq(versions.scala212, versions.scala213))
  .jsPlatform(Seq(versions.scala212, versions.scala213))

lazy val root = project
  .in(file("."))
  .aggregate(`zio-test-macros`.projectRefs: _*)
  .settings(
    name := "zio-test-macros",
    publish / skip := true,
    compile / skip := true
  )
