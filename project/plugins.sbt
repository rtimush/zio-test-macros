resolvers += Resolver.bintrayIvyRepo("rallyhealth", "sbt-plugins")

addSbtPlugin("com.rallyhealth.sbt" % "sbt-git-versioning" % "1.4.0")
addSbtPlugin("com.eed3si9n"        % "sbt-projectmatrix"  % "0.7.0")
addSbtPlugin("org.scala-js"        % "sbt-scalajs"        % "1.3.1")
addSbtPlugin("org.scalameta"       % "sbt-scalafmt"       % "2.4.2")