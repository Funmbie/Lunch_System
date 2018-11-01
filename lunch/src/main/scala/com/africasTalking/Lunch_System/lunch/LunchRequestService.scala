package com.africasTalking.Lunch_System.lunch

import akka.actor.{Actor, ActorLogging}
import com.africasTalking.Lunch_System.core.utils.CoreServices
import com.africasTalking.Lunch_System.lunch.marshalling.WebJsonImplicits

object LunchRequestService {

  case object ListServiceRequest

  case class ListServiceResponse(list: List[String])

  case class OrderServiceRequest(list: List[String])

  case class OrderServiceResponse(total: BigDecimal)

  case class ProceedServiceRequest(
                             number: String,
                             amount: Double
                           )

  case class ProceedServiceResponse(
                              status: String,
                              description: String,
                              transactionId: String,
                              providerChannel: String
                            )
}

class LunchRequestService extends Actor with ActorLogging with CoreServices with WebJsonImplicits {

  import LunchRequestService._
  import com.africasTalking.Lunch_System.core.db.DBRequestServices

  val dbRef = new DBRequestServices

  def receive = {
    case ListServiceRequest                                    =>
      val senderObject = sender()
      val responseList = dbRef.getAll
      senderObject ! ListServiceResponse(responseList)

    case OrderServiceRequest(x: List[String])                  =>
      val senderObject = sender()
      dbRef.itExists(x) match{
        case true=> senderObject ! OrderServiceResponse(dbRef.total(x))
        case false=> println(s"$senderObject is trying to process an order request with invalid entity")
      }

    case ProceedServiceRequest(number: String, amount: Double) =>
      val senderObject = sender()
      //Send to Gateway
  }
}
