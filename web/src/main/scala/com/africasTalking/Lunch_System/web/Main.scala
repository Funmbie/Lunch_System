package com.africasTalking.Lunch_System
package web

import scala.language.postfixOps
import scala.util.{Failure, Success}

import akka.http.scaladsl.Http

import com.africasTalking.Lunch_System.core.utils.LunchConfig


class Main extends WebService{

  import system.dispatcher

  private[this] var started: Boolean = false

  def start= {
    if (!started) {
      val bindingFuture = Http().bindAndHandle(route, LunchConfig.webInterface, LunchConfig.webPort)
      bindingFuture.onComplete {
        case Success(serverBinding) => println(s"Server Started. Listening to ${serverBinding.localAddress}")
                                        started = true
        case Failure(error)         => println(s"error: ${error.getMessage}")
      }
    }
  }

  def stop = {
    if (started) {
      started = false
      system.terminate()
    }
  }
}

object Main extends App {
  val main = new Main
  main.start
}