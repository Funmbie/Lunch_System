package com.africasTalking.Lunch_System
package web

import scala.concurrent.Future
import scala.util.{Failure, Success}

import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal

import com.africasTalking.Lunch_System.core.utils.CoreServices
import com.africasTalking.Lunch_System.lunch.LunchRequestGateway.ATPaymentServiceResponse
import com.africasTalking.Lunch_System.lunch.marshalling.WebJsonImplicits

object Client extends App
    with CoreServices
    with WebJsonImplicits {

  import system.dispatcher

  val responseFut = Http().singleRequest(
    HttpRequest(
      uri = Uri("http://localhost:3306/list")
    ))

  responseFut onComplete {
    case Success(response) => val responseList = response.entity
      println(responseList)
    case Failure(error) => println(s"Error Encountered: $error")
  }

  import com.africasTalking.Lunch_System.lunch.LunchRequestService._

  private var total: Double = 0
  var list: List[String] = List()


  val request = HttpRequest(method = HttpMethods.POST, uri = "http://localhost:3306/list")
  val listReq = Http().singleRequest(request)

  listReq onComplete {
    case Failure(ex) => println(s"Failed to post. Reason: $ex")
    case Success(response) =>
      val resFut: Future[ListServiceResponse] = Unmarshal(response.entity).to[ListServiceResponse]
      resFut onComplete {
        case Success(value) =>
          list = value.list
        case Failure(exception) => println(s"Error Encountered: $exception")
      }
  }

  val req = OrderServiceRequest(list)

  def postOrderReq: Future[HttpResponse] =
    Marshal(req).to[RequestEntity] flatMap { entity =>
      val request = HttpRequest(method = HttpMethods.POST, uri = "http://localhost:3306/list/order", entity = entity)
      Http().singleRequest(request)
    }

  postOrderReq onComplete {
    case Failure(ex) => println(s"Failed to post. Reason: $ex")
    case Success(response) =>
      val resFut: Future[OrderServiceResponse] = Unmarshal(response.entity).to[OrderServiceResponse]
      resFut onComplete {
        case Success(value) =>
          println(s"${value}")
          total += value.total.toDouble
          println(total)
        case Failure(exception) => println(s"Error Encountered: $exception")
      }
  }

  val proceedreq = ProceedServiceRequest("+254701951089", 100)

  def postProceedReq: Future[HttpResponse] =
    Marshal(proceedreq).to[RequestEntity] flatMap { entity =>
      val request = HttpRequest(method = HttpMethods.POST, uri = "http://localhost:3306/order/proceed", entity = entity)
      Http().singleRequest(request)
    }

  postProceedReq onComplete {
    case Failure(ex) => println(s"Failed to post. Reason: $ex")
    case Success(response) => {
      val res: Future[ATPaymentServiceResponse] = Unmarshal(response.entity).to[ATPaymentServiceResponse]
      res onComplete {
        case Success(value) =>
          value.status match {
            case "PendingConfirmation" =>
              println("Please complete the transaction on your device")
            //Listening aspect
          }

        case Failure(ex) =>
          println(s"Error un-marshalling: $ex")
      }
    }
  }
}