package com.africasTalking.Lunch_System
package lunch

import scala.concurrent.duration._

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import akka.util.Timeout

import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

import com.africasTalking.Lunch_System.lunch.LunchRequestService._

class LunchRequestServiceSpec extends TestKit(ActorSystem("LunchRequestServiceSpec"))
  with ImplicitSender
  with WordSpecLike
  with Matchers
  with BeforeAndAfterAll{

  implicit val timeout = Timeout(15 seconds)

  val lunchServiceActor = system.actorOf(Props[LunchRequestService])

  "Lunch Request Service" must{
    "Respond with a ListService Response where the List is not empty when given a ListService Request" in{
      lunchServiceActor ! ListServiceRequest
      expectMsg(ListServiceResponse(List("Matoke", "Ugali", "Chicken", "Sukuma", "Rice", "Spaghetti", "Pilau", "Beans", "Cabbage", "Beef")))
    }

    "Respond with an OrderService Response when given an OrderService Request" in{
      lunchServiceActor ! OrderServiceRequest(List("Chicken","Rice","Spaghetti","Beans"))
      expectMsg(OrderServiceResponse(300))
    }
  }
}