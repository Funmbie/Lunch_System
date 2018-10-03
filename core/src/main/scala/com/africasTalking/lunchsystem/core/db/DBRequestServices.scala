package com.africasTalking.lunchsystem.core.db

class DBRequestServices {

  import LocalDB._

  def itExists(list: List[String]): Boolean = {
    //returns a bool if all items in the list exist in the DB
    val dbList: List[String] = local_DB.keys toList
    val filteredList = dbList.filter((x) => !local_DB.contains(x))

    filteredList.length match {
      case 0 => return true
      case _ => return false
    }
  }

  def total(list: List[String]):Double = {
    //assumes the list is already sorted and calculates the total for the list
    var total:Double = 0
    list.foreach((l)=>total += local_DB(l))
    total
  }

  def getAll:List[String] = {
    //Gets a list of all the items in the db
    local_DB.keys toList
  }
}
