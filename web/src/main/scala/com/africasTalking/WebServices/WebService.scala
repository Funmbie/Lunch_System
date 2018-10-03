package com.africasTalking.WebServices

import akka.actor.Props
import akka.pattern.ask
import akka.http.scaladsl.server.Directives._
import com.africasTalking.LunchServiceRequests.LunchServiceRequest
import com.africasTalking.lunchsystem.core.utils.CoreServices
import com.africasTalking.LunchServiceRequests.marshalling.WebJsonImplicits
import scala.language.postfixOps

class WebService extends CoreServices with WebJsonImplicits {

  import LunchServiceRequest._

  private val lunchServiceRequest = system.actorOf(Props[LunchServiceRequest])


  lazy val route = {

    path("list") {
      get {
        complete {
          (lunchServiceRequest ? ListService).mapTo[ListResponse]
        }
      }
    } ~
      path("list" / "order") {
        post {
          entity(as[OrderRequest]) { request =>
            complete {
              (lunchServiceRequest ? request).mapTo[OrderResponse]
            }
          }
        }
      } ~
      path("order" / "proceed") {
        post {
          entity(as[ProceedRequest]) { request =>
            complete {
              (lunchServiceRequest ? request).mapTo[ProceedResponse]
            }
          }
        }
      }

  }
}
