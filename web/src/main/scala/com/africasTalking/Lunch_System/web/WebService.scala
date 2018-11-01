package com.africasTalking.Lunch_System.web

import scala.concurrent.duration._
import scala.language.postfixOps

import akka.actor.Props
import akka.pattern.ask
import akka.http.scaladsl.server.Directives._
import akka.util.Timeout

import com.africasTalking.Lunch_System.core.utils.{CoreServices, LunchConfig}
import com.africasTalking.Lunch_System.lunch.LunchRequestGateway.ATPaymentServiceResponse
import com.africasTalking.Lunch_System.lunch.LunchRequestService
import com.africasTalking.Lunch_System.lunch.marshalling.WebJsonImplicits


class WebService extends CoreServices with WebJsonImplicits {

  import com.africasTalking.Lunch_System.lunch.LunchRequestService._

  private val lunchServiceRequest = system.actorOf(Props[LunchRequestService])
  implicit val timeout            = Timeout(LunchConfig.timeout seconds)

  lazy val route                  = {

    path("list") {
      get {
        complete {
          (lunchServiceRequest ? ListServiceRequest).mapTo[ListServiceResponse]
        }
      }
    } ~
      path("order") {
        post {
          entity(as[OrderServiceRequest]) { request   =>
            complete {
              (lunchServiceRequest ? request).mapTo[OrderServiceResponse]
            }
          }
        }
      } ~
      path("proceed") {
        post {
          entity(as[ProceedServiceRequest]) { request =>
            complete {
              (lunchServiceRequest ? request).mapTo[ATPaymentServiceResponse]
            }
          }
        }
      }

  }
}
