package com.africasTalking.LunchServiceRequests.marshalling

import spray.json.{DefaultJsonProtocol, JsonFormat}
import com.africasTalking.LunchServiceRequests.LunchServiceRequest._
import akka.http.scaladsl.marshallers.sprayjson._

trait WebJsonImplicits extends DefaultJsonProtocol with SprayJsonSupport {

  implicit val LisServiceFormat = jsonFormat0(ListService)
  implicit val listResponseFormat = jsonFormat1(ListResponse)
  implicit val OderRequestFormat = jsonFormat1(OrderRequest)
  implicit val orderResponseFormat = jsonFormat1(OrderResponse)
  implicit val ProceedRequestFormat = jsonFormat2(ProceedRequest)
  implicit val ProceedResponseFormat = jsonFormat4(ProceedResponse)
}
