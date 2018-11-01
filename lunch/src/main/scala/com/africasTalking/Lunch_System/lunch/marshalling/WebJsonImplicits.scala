package com.africasTalking.Lunch_System.lunch.marshalling

import spray.json.{DefaultJsonProtocol, JsonFormat}
import com.africasTalking.LunchServiceRequests.LunchServiceRequest._
import akka.http.scaladsl.marshallers.sprayjson._
import com.africasTalking.Lunch_System.lunch.LunchRequestService._

trait WebJsonImplicits extends DefaultJsonProtocol with SprayJsonSupport {

  implicit val LisServiceFormat = jsonFormat0(ListServiceRequest)
  implicit val listResponseFormat = jsonFormat1(ListServiceResponse)
  implicit val OderRequestFormat = jsonFormat1(OrderServiceRequest)
  implicit val orderResponseFormat = jsonFormat1(OrderServiceResponse)
  implicit val ProceedRequestFormat = jsonFormat2(ProceedServiceRequest)
  implicit val ProceedResponseFormat = jsonFormat4(ProceedServiceResponse)
}
