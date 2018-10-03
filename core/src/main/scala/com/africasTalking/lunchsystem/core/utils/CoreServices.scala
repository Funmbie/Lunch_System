package com.africasTalking.lunchsystem.core.utils

import akka.actor.{ActorSystem, Props}
import akka.stream.ActorMaterializer
import akka.util.Timeout
import scala.concurrent.duration._

trait CoreServices{
  implicit val system = ActorSystem()
  implicit val executionContext = system.dispatcher
  implicit val materializer = ActorMaterializer()
  implicit val timeout = Timeout(30 seconds)
}
