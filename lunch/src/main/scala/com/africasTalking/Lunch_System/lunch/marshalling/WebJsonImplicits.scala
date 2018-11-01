package com.africasTalking.Lunch_System.lunch
package marshalling

import akka.http.scaladsl.marshallers.sprayjson._

import spray.json.DefaultJsonProtocol

import com.africasTalking.Lunch_System.lunch.LunchRequestGateway._
import com.africasTalking.Lunch_System.lunch.LunchRequestService._

trait WebJsonImplicits extends DefaultJsonProtocol with SprayJsonSupport {

  implicit val listResponseFormat      = jsonFormat1(ListServiceResponse)
  implicit val OderRequestFormat       = jsonFormat1(OrderServiceRequest)
  implicit val orderResponseFormat     = jsonFormat1(OrderServiceResponse)
  implicit val ProceedRequestFormat    = jsonFormat2(ProceedServiceRequest)
  implicit val atPaymentResponseFormat = jsonFormat4(ATPaymentServiceResponse)
  implicit val atPaymentRequestFormat  = jsonFormat5(ATPaymentServiceRequest)
}
