package com.africasTalking.Lunch_System
package web

import scala.language.postfixOps
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

import akka.actor.{ActorRefFactory, ActorSystem}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

import com.africasTalking.Lunch_System.core.utils.{CoreServices, LunchConfig}


class Main extends WebServiceT {
  private[this] var started: Boolean       = false
  val applicationName                      = "LunchHandling"
  implicit val actorSystem                 = ActorSystem(s"$applicationName-system")
  override def actorRefFactory = actorSystem

  def start= {
    if (!started) {
      implicit val materializer = ActorMaterializer()

      Http().bindAndHandle(
        route,
        LunchConfig.webInterface,
        LunchConfig.webPort
      ) onComplete {
        case Success(serverBinding)    => println(s"Server Started. Listening to ${serverBinding.localAddress}")
                                        started = true
        case Failure(error)            => println(s"error: ${error.getMessage}")
      }
    }
  }

  def stop = {
    if (started) {
      started = false
      actorSystem.terminate()
    }
  }
}

object Main extends App {
  val main = new Main
  main.start
}