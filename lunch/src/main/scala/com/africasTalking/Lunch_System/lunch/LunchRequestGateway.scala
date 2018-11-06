package com.africasTalking.Lunch_System
package lunch

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

import akka.actor.{Actor, ActorLogging}
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer

import com.africasTalking.Lunch_System.core.utils._
import com.africasTalking.Lunch_System.lunch.LunchRequestGateway._
import com.africasTalking.Lunch_System.lunch.marshalling.WebJsonImplicits


object LunchRequestGateway{
  case class LunchServiceRequest(amount:Double,number:String)

  case class ATPaymentServiceRequest(
    username:String,
    productName:String,
    phoneNumber:String,
    currencyCode:String,
    amount: Double
  )

  case class ATPaymentServiceResponse(
     status: String,
     description: String,
     transactionId: String,
     providerChannel: String
   )
}

class LunchRequestGateway extends Actor
    with ActorLogging
    with WebJsonImplicits{

  implicit val actorSystem      = context.system
  implicit val materializer     = ActorMaterializer()


  def receive: Receive = {
    case req: LunchServiceRequest  =>

      val atPayment = ATPaymentServiceRequest(
       LunchConfig.sandboxapiUsername,
       LunchConfig.productName,
       req.number,
       LunchConfig.currencyCode,
       req.amount
     )

      Marshal(atPayment).to[RequestEntity] map { entity => Http().singleRequest( HttpRequest(
          headers = List(headers.RawHeader("apiKey", LunchConfig.sandboxapiKey)),
          method = HttpMethods.POST,
          uri = Uri(LunchConfig.paymentsSandboxUrl),
          entity = entity
      )) onComplete {

      case Success(value)         => Unmarshal(value.entity).to[ATPaymentServiceResponse] onComplete {
          case Success(response)  => sender ! response
          case Failure(exception) => log.info(s"Error: $exception")
      }

      case Failure(ex)            =>
        log.info(s"POST to Africa's Talking Unsuccessful: $ex")
    }
  }
  }
}
