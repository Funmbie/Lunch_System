package com.africasTalking.Lunch_System.core
package utils

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

trait CoreServices{
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
}
