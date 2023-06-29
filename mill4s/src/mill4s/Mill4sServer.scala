package mill4s

import cats.effect.*
import cats.syntax.all.*
import com.comcast.ip4s.*
import fs2.io.net.Network
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits.*
import org.http4s.server.middleware.Logger

object Mill4sServer extends IOApp:

  def run(args: List[String]): IO[ExitCode] = {
    for {
      client <- EmberClientBuilder.default[IO].build
      helloWorldAlg = HelloWorld.impl[IO]
      jokeAlg = Jokes.impl[IO](client)

      // Combine Service Routes into an HttpApp.
      // Can also be done via a Router if you
      // want to extract a segments not checked
      // in the underlying routes.
      httpApp = (
        Mill4sRoutes.helloWorldRoutes[IO](helloWorldAlg) <+>
          Mill4sRoutes.jokeRoutes[IO](jokeAlg)
      ).orNotFound

      // With Middlewares in place
      finalHttpApp = Logger.httpApp(true, true)(httpApp)

      _ <-
        EmberServerBuilder
          .default[IO]
          .withHost(ipv4"0.0.0.0")
          .withPort(port"8080")
          .withHttpApp(finalHttpApp)
          .build
    } yield ()
  }.useForever
