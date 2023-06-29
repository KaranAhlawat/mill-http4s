import mill._
import scalalib._


val Http4sVersion = "0.23.21"
val LogbackVersion = "1.4.8"

object mill4s extends ScalaModule {
  def scalaVersion = "3.3.0"

  def mainClass = Some("mill4s.Mill4sServer")

  def ivyDeps = Agg(
    ivy"org.http4s::http4s-ember-server:${Http4sVersion}",
    ivy"org.http4s::http4s-ember-client:${Http4sVersion}",
    ivy"org.http4s::http4s-circe:${Http4sVersion}",
    ivy"org.http4s::http4s-dsl:${Http4sVersion}",
    ivy"ch.qos.logback:logback-classic:${LogbackVersion}"
  )
}
