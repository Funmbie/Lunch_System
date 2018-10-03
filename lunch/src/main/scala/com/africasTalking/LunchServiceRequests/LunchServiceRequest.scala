package com.africasTalking.LunchServiceRequests

import akka.actor.{Actor, ActorLogging}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.util.ByteString
import com.africasTalking.LunchServiceRequests.marshalling.WebJsonImplicits
import com.africasTalking.lunchsystem.core.utils.{CoreServices, LunchConfig}

import scala.util.{Failure, Success}

object LunchServiceRequest {

  case class ListService()

  case class ListResponse(list: List[String])

  case class OrderRequest(list: List[String])

  case class OrderResponse(total: BigDecimal)

  case class ProceedRequest(number: String, amount: Double)

  case class ProceedResponse(status: String, description: String, transactionId: String, providerChannel: String)

}

class LunchServiceRequest extends Actor with ActorLogging with CoreServices with LunchConfig with WebJsonImplicits {

  import LunchServiceRequest._
  import com.africasTalking.lunchsystem.core.db.DBRequestServices

  val dbRef = new DBRequestServices

  def receive = {
    case ListService => {
      val senderObject = sender()
      val responseList = dbRef.getAll
      senderObject ! ListResponse(responseList)
    }

    case OrderRequest(x: List[String]) => {
      val senderObject = sender()
      dbRef.itExists(x) match{
        case true=> senderObject ! OrderResponse(dbRef.total(x))
        case false=> println(s"$senderObject is trying to process an order request with invalid entity")
      }
    }

    case ProceedRequest(number: String, amount: Double) => {
      val senderObject = sender()

      val requestBody = s"""{ "username": "$sandboxapiUsername","productName":"$productName","phoneNumber":"$number","currencyCode":"$currencyCode","amount":$amount }"""
      val request = Http().singleRequest(
        HttpRequest(
          headers = List(headers.RawHeader("apiKey", sandboxapiKey)),
          method = HttpMethods.POST,
          uri = Uri(paymentsSandboxUrl),
          entity = HttpEntity(ContentTypes.`application/json`, ByteString(requestBody))
        )
      )

      request onComplete {
        case Success(value) =>
          val res_ = Unmarshal(value.entity).to[ProceedResponse]
          res_ onComplete {
            case Success(response) => senderObject ! response
            case Failure(exception) => println(s"Error: $exception")
          }

        case Failure(ex) =>
          println(s"Error!!!! $ex")
      }
    }
  }
}
