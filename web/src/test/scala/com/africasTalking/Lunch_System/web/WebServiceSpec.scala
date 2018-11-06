package com.africasTalking.Lunch_System
package web

import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.stream.ActorMaterializer
import org.scalatest.{Matchers, WordSpec}

class WebServiceSpec extends WordSpec
  with Matchers
  with ScalatestRouteTest
  with WebServiceT{

  def actorRefFactory  = system

  "LunchWebSystem" must{
    "Not handle requests made to the baseurl" in{
      Put("/list")~>Route.seal(route) ~> check{
        status shouldEqual StatusCodes.MethodNotAllowed
      }
    }

    "Handle requests made to a valid endpoint" in{
      Get("/list") ~> Route.seal(route)~>check {
        status shouldEqual StatusCodes.OK
      }
    }
  }
}