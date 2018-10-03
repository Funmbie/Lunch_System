package com.africasTalking.lunchsystem.core.utils

import com.typesafe.config.ConfigFactory

trait LunchConfig {
  val config = ConfigFactory.load
  config.checkValid(ConfigFactory.defaultReference)

  val webInterface = config.getString("lunch.web.interface")
  val webPort = config.getInt("lunch.web.port")
  val sandboxapiKey = config.getString("at.sandbox.apikey")
  val sandboxapiUsername = config.getString("at.sandbox.username")
  val currencyCode = config.getString("default.currencyCode")
  val productName = config.getString("default.productName")
  val paymentsSandboxUrl = config.getString("payments.sandbox.url")
}
