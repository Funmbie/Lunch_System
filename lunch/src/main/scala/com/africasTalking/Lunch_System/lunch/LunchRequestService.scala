package com.africasTalking.Lunch_System
package lunch

import scala.concurrent.duration._

import akka.actor.{Actor, ActorLogging, Props}
import akka.pattern.{ask, pipe}
import akka.util.Timeout

import com.africasTalking.Lunch_System.core.utils.{CoreServices, LunchConfig}
import com.africasTalking.Lunch_System.lunch.LunchRequestGateway.ATPaymentServiceResponse

object LunchRequestService {

  case  object ListServiceRequest

  case  class  ListServiceResponse(list: List[String])

  case  class  OrderServiceRequest(list: List[String])

  case  class  OrderServiceResponse(total: BigDecimal)

  case  class  ProceedServiceRequest(
      number:  String,
      amount:  Double
   )
}

class LunchRequestService extends Actor
  with ActorLogging
  with CoreServices{

  import LunchRequestService._
  import com.africasTalking.Lunch_System.core.db.DBRequestServices

  import system.dispatcher

  implicit val timeout:Timeout = Timeout(LunchConfig.timeout seconds)
  val dbRef = new DBRequestServices

  def receive: Receive = {
    case ListServiceRequest                         =>
      val senderObject = sender()
      val responseList = dbRef.getAll
      senderObject ! ListServiceResponse(responseList)

    case OrderServiceRequest(x: List[String])       =>
      val senderObject = sender()
      if (dbRef.itExists(x)) {
        senderObject ! OrderServiceResponse(dbRef.total(x))
      } else {
        log.info(s"$senderObject is trying to process an order request with invalid entity")
      }

    case req:ProceedServiceRequest                  =>
      val senderObject        = sender()
      val lunchRequestGateway = system.actorOf(Props[LunchRequestGateway])
      (lunchRequestGateway ? req).mapTo[ATPaymentServiceResponse] pipeTo senderObject
  }
}