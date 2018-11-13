package com.africasTalking.Lunch_System.core
package db

object LocalDB {
  val local_DB = Map(
    Food.Rice.toString      -> Food.Rice.id.toDouble,
    Food.Spaghetti.toString -> Food.Spaghetti.id.toDouble,
    Food.Pilau.toString     -> Food.Pilau.id.toDouble,
    Food.Ugali.toString     -> Food.Ugali.id.toDouble,
    Food.Matoke.toString    -> Food.Matoke.id.toDouble,
    Food.Chicken.toString   -> Food.Chicken.id.toDouble,
    Food.Beef.toString      -> Food.Beef.id.toDouble,
    Food.Beans.toString     -> Food.Beans.id.toDouble,
    Food.Cabbage.toString   -> Food.Cabbage.id.toDouble,
    Food.Sukuma.toString    -> Food.Sukuma.id.toDouble
  )
}

object Food extends Enumeration {
  val Rice = Value(100)
  val Spaghetti = Value(90)
  val Pilau = Value(110)
  val Ugali = Value(70)
  val Matoke = Value(60)
  val Chicken = Value(80)
  val Beef = Value(50)
  val Beans = Value(30)
  val Cabbage = Value(20)
  val Sukuma = Value(10)
}
