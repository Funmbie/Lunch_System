package com.africasTalking.Lunch_System.web

import akka.actor.Props
import akka.pattern.ask
import akka.http.scaladsl.server.Directives._
import com.africasTalking.LunchServiceRequests.LunchServiceRequest
import com.africasTalking.Lunch_System.core.utils.CoreServices
import com.africasTalking.Lunch_System.lunch.LunchRequestService
import com.africasTalking.Lunch_System.lunch.marshalling.WebJsonImplicits

import scala.language.postfixOps

class WebService extends CoreServices with WebJsonImplicits {

  import com.africasTalking.Lunch_System.lunch.LunchRequestService._

  private val lunchServiceRequest = system.actorOf(Props[LunchRequestService])


  lazy val route = {

    path("list") {
      get {
        complete {
          (lunchServiceRequest ? ListServiceRequest).mapTo[ListServiceResponse]
        }
      }
    } ~
      path("list" / "order") {
        post {
          entity(as[OrderServiceRequest]) { request =>
            complete {
              (lunchServiceRequest ? request).mapTo[OrderServiceResponse]
            }
          }
        }
      } ~
      path("order" / "proceed") {
        post {
          entity(as[ProceedServiceRequest]) { request =>
            complete {
              (lunchServiceRequest ? request).mapTo[ProceedServiceResponse]
            }
          }
        }
      }

  }
}
