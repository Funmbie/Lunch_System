package com.africasTalking.WebServices

import akka.http.scaladsl.Http
import com.africasTalking.lunchsystem.core.utils.{LunchConfig}
import scala.language.postfixOps
import scala.util.{Failure, Success}


class Main extends WebService with LunchConfig {
  private[this] var started: Boolean = false

  def start = {
    if (!started) {
      val bindingFuture = Http().bindAndHandle(route, webInterface, webPort)
      bindingFuture.onComplete {
        case Success(serverBinding) => println(s"Server Started. Listening to ${serverBinding.localAddress}")
          started = true
        case Failure(error) => println(s"error: ${error.getMessage}")
      }
    }
  }

  def stop = {
    if (!started) {
      started = false
      system.terminate()
    }
  }
}

object Main extends App {
  lazy val main = new Main()
  main.start
}
