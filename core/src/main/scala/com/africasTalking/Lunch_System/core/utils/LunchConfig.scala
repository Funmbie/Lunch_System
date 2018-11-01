package com.africasTalking.Lunch_System.core.utils

import com.typesafe.config.ConfigFactory

object LunchConfig {
  val config             = ConfigFactory.load
  config.checkValid(ConfigFactory.defaultReference)

  val currencyCode       = config.getString("default.currencyCode")
  val productName        = config.getString("default.productName")
  val paymentsSandboxUrl = config.getString("payments.sandbox.url")
  val sandboxapiKey      = config.getString("at.sandbox.apikey")
  val sandboxapiUsername = config.getString("at.sandbox.username")
  val webInterface       = config.getString("lunch.web.interface")
  val webPort            = config.getInt("lunch.web.port")
}
