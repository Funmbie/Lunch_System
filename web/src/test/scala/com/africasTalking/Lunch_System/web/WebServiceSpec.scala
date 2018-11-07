package com.africasTalking.Lunch_System
package web

import akka.http.scaladsl.model.{FormData, StatusCodes}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.africasTalking.Lunch_System.lunch.LunchRequestService
import com.africasTalking.Lunch_System.lunch.LunchRequestService._
import org.scalatest.{Matchers, WordSpec}
import sun.text.resources.FormatData

class WebServiceSpec extends WordSpec
  with Matchers
  with ScalatestRouteTest
  with WebServiceT{

  def actorRefFactory  = system

  "LunchWebSystem" must{
    "Not handle requests made to other endpoints" in{
      Put("/other")~>Route.seal(route) ~> check{
        status shouldEqual StatusCodes.NotFound
      }
    }

    "Handle requests made to a valid endpoints" in{
      Get("/list") ~> Route.seal(route) ~> check {
        status shouldEqual StatusCodes.OK
        responseAs[ListServiceResponse] shouldEqual ListServiceResponse(List("Matoke", "Ugali", "Chicken", "Sukuma", "Rice", "Spaghetti", "Pilau", "Beans", "Cabbage", "Beef"))
      }
    }

    "Respond with a total when sent a valid list through the endpoint" in{
      Post("/order") ~> Route.seal(route) ~> check{

      }
    }
  }
}