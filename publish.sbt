ThisBuild / publishTo := sonatypePublishToBundle.value
ThisBuild / publishMavenStyle := true

ThisBuild / licenses := List(
  "BSD 3-Clause" -> url("https://github.com/rtimush/zio-test-macros/blob/master/LICENSE")
)
ThisBuild / homepage := Some(url("https://github.com/rtimush/zio-test-macros"))
ThisBuild / developers := List(
  Developer("rtimush", "Roman Timushev", "rtimush@gmail.com", url("https://github.com/rtimush"))
)
ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/rtimush/zio-test-macros"),
    "scm:git:https://github.com/rtimush/zio-test-macros.git",
    Some("scm:git:git@github.com:rtimush/zio-test-macros.git")
  )
)
