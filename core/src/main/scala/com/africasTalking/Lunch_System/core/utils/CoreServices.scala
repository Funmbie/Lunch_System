package com.africasTalking.Lunch_System.core
package utils

import akka.actor.ActorRefFactory

trait CoreServices {
  def actorRefFactory:ActorRefFactory
}
