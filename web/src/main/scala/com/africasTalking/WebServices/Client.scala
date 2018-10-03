package com.africasTalking.WebServices

import spray.json._
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import com.africasTalking.LunchServiceRequests.marshalling.WebJsonImplicits
import com.africasTalking.lunchsystem.core.utils.CoreServices
import scala.concurrent.Future
import scala.util.{Failure, Success}

object Client extends App with CoreServices with WebJsonImplicits {
  //First Request
  val responseFut = Http().singleRequest(
    HttpRequest(
      uri = Uri("http://localhost:3306/list")
    ))

  responseFut onComplete {
    case Success(response) => val responseList = response.entity
      println(responseList)
    case Failure(error) => println(s"Error Encountered: $error")
  }

  //Second Request
  import com.africasTalking.LunchServiceRequests.LunchServiceRequest._

  private var total: Double = 0
  var list: List[String] = List()


  val request = HttpRequest(method = HttpMethods.POST, uri = "http://localhost:3306/list")
  val listReq = Http().singleRequest(request)

  listReq onComplete {
    case Failure(ex) => println(s"Failed to post. Reason: $ex")
    case Success(response) =>
      val resFut: Future[ListResponse] = Unmarshal(response.entity).to[ListResponse]
      resFut onComplete {
        case Success(value) =>
          list = value.list
        case Failure(exception) => println(s"Error Encountered: $exception")
      }
  }

  val req = OrderRequest(list)

  def postOrderReq: Future[HttpResponse] =
    Marshal(req).to[RequestEntity] flatMap { entity =>
      val request = HttpRequest(method = HttpMethods.POST, uri = "http://localhost:3306/list/order", entity = entity)
      Http().singleRequest(request)
    }

  postOrderReq onComplete {
    case Failure(ex) => println(s"Failed to post. Reason: $ex")
    case Success(response) =>
      val resFut: Future[OrderResponse] = Unmarshal(response.entity).to[OrderResponse]
      resFut onComplete {
        case Success(value) =>
          println(s"${value}")
          total += value.total.toDouble
          println(total)
        case Failure(exception) => println(s"Error Encountered: $exception")
      }
  }

  val proceedreq = ProceedRequest("+254701951089", 100)

  def postProceedReq: Future[HttpResponse] =
    Marshal(proceedreq).to[RequestEntity] flatMap { entity =>
      val request = HttpRequest(method = HttpMethods.POST, uri = "http://localhost:3306/order/proceed", entity = entity)
      Http().singleRequest(request)
    }

  postProceedReq onComplete {
    case Failure(ex) => println(s"Failed to post. Reason: $ex")
    case Success(response) => {
      val res: Future[ProceedResponse] = Unmarshal(response.entity).to[ProceedResponse]
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