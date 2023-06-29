package mill4s

import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp.Simple:
  val run = Mill4sServer.run[IO]
