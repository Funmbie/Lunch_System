package com.africasTalking.Lunch_System
package lunch

import scala.concurrent.duration._
import scala.concurrent.Future
import akka.actor.Props
import akka.http.scaladsl.testkit.{RouteTestTimeout, ScalatestRouteTest}
import akka.pattern.ask
import akka.testkit.TestProbe
import akka.util.Timeout
import com.africasTalking.Lunch_System.lunch.LunchRequestGateway.{ATPaymentServiceResponse, LunchServiceRequest}
import org.scalatest.{Matchers, WordSpec}
import com.africasTalking.Lunch_System.lunch.marshalling.WebJsonImplicits


class LunchRequestGatewaySpec extends WordSpec
  with ScalatestRouteTest
  with Matchers
  with WebJsonImplicits {

  implicit val timeout  = Timeout(15 seconds)
  val lunchGatewayActor = system.actorOf(Props[LunchRequestGateway])

  "A Lunch Request Gateway" must{
    "Be able to send request to AT payments and receive a response" in{

    }
  }
}