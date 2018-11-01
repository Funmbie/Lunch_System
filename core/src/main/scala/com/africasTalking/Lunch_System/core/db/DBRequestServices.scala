package com.africasTalking.Lunch_System.core
package db

class DBRequestServices {

  import LocalDB._

  def getAll:List[String] = local_DB.keys.toList

  def itExists(list: List[String]): Boolean = {
    val dbList: List[String] = local_DB.keys.toList
    val filteredList         = dbList.filter(!local_DB.contains(_))

    filteredList.length match {
      case 0 => true
      case _ => false
    }
  }

  def total(list: List[String]):Double = list.map(local_DB(_)).fold(0.0)(_+_)
}
