package com.africasTalking.Lunch_System.lunch

import akka.actor.Actor
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.util.ByteString
import com.africasTalking.Lunch_System.lunch.LunchRequestService.ProceedServiceResponse
import com.africasTalking.Lunch_System.core.utils.{CoreServices, LunchConfig}
import com.africasTalking.Lunch_System.lunch.LunchRequestGateway.LunchServiceRequest

import scala.util.{Failure, Success}

object LunchRequestGateway{
  case class LunchServiceRequest(amount:Double,number:String)
}

class LunchRequestGateway extends Actor with CoreServices{
  import LunchRequestService._

  def receive = {
    case req: LunchServiceRequest  =>
    Marshal(Map(
      "username" -> LunchConfig.sandboxapiUsername,
      "productName" -> LunchConfig.productName,
      "phoneNumber" -> req.number,
      "currencyCode" -> LunchConfig.currencyCode,
      "amount" -> req.amount
  )).to[RequestEntity] map { entity =>
      val request = Http().singleRequest(
        HttpRequest(
          headers = List(headers.RawHeader("apiKey", LunchConfig.sandboxapiKey)),
          method = HttpMethods.POST,
          uri = Uri(LunchConfig.paymentsSandboxUrl),
          entity = entity
        )
      )

    request onComplete {
      case Success(value) =>
        val res_ = Unmarshal(value.entity).to[ProceedServiceResponse]
        res_ onComplete {
          case Success(response) => sender ! response
          case Failure(exception) => println(s"Error: $exception")
        }

      case Failure(ex) =>
        println(s"Error!!!! $ex")
    }
  }
  }
}
