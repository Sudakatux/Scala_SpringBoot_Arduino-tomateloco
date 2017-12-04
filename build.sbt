name := "spring-boot-web"

version := "1.0"

scalaVersion := "2.10.2"

sbtVersion := "0.13.1"


libraryDependencies ++= Seq(
  "org.springframework.boot" % "spring-boot-starter-web" % "1.2.1.RELEASE",
  "org.springframework.boot" % "spring-boot-starter-websocket" % "1.2.1.RELEASE",
  "org.mongodb" % "casbah-core_2.10" % "2.5.0",
  "com.typesafe.akka" % "akka-actor_2.10" % "2.3.9",
  "org.springframework.boot" % "spring-boot-starter-tomcat" % "1.2.1.RELEASE" % "provided",
  "javax.servlet" % "javax.servlet-api" % "3.0.1" % "provided"
)


libraryDependencies ++= Seq(
  "org.apache.tomcat.embed" % "tomcat-embed-core"         % "7.0.53" % "container",
  "org.apache.tomcat.embed" % "tomcat-embed-logging-juli" % "7.0.53" % "container",
  "org.apache.tomcat.embed" % "tomcat-embed-jasper"       % "7.0.53" % "container"
)