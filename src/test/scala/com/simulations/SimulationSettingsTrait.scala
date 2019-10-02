package com.simulations

import com.helpers.Common
import com.typesafe.config.ConfigFactory
import io.gatling.core.Predef._
import io.gatling.core.controller.inject.InjectionStep
import io.gatling.core.filter.WhiteList
import io.gatling.http.Predef._

import scala.concurrent.duration._

/**
  * Created by Juri on 02/10/2019.
  */

trait SimulationSettingsTrait {

  val environmentUnderTest = Common.prop("sut", "live")
  val vU  = Common.prop("vu", 2)
  val vU1 = Common.prop("vu1", 1)
  val vU2 = Common.prop("vu2", 1)
  val vU3 = Common.prop("vu3", 1)
  val vU4 = Common.prop("vu4", 1)
  val vU5 = Common.prop("vu5", 1)
  val vU6 = Common.prop("vu6", 1)
  val vU7 = Common.prop("vu7", 1)
  val vU8 = Common.prop("vu8", 1)
  val vU9 = Common.prop("vu9", 1)
  val vU10 = Common.prop("vu10", 1)
  val vU11 = Common.prop("vu11", 1)
  val vU12 = Common.prop("vu12", 1)
  val vU13 = Common.prop("vu13", 1)
  val vU14 = Common.prop("vu14", 1)

  val singleRun = Common.prop("single_run", false)
  val rampDurationMinutes = Common.prop("ramp_duration", 1)
  val paceDurationMinutes = Common.prop("pace_duration", 2)
  val runDurationMinutes = Common.prop("run_duration", 1)
  val warmUpTimeSeconds = Common.prop("warm_up_time", 4)

  val numberOfBetsPerUser = Common.prop("bets_per_user", 1)

  val conf = ConfigFactory.load("environment.conf")
  val baseUri : String = Common.getConfigFromFile("environment.conf", "baseURL")

  def rampProfile(scenarioVU: Int) : Iterable[InjectionStep] =
    List(
      nothingFor(warmUpTimeSeconds seconds),
      rampUsers(scenarioVU) over (rampDurationMinutes minutes),
      nothingFor(paceDurationMinutes minutes),
      rampUsers(scenarioVU) over (rampDurationMinutes minutes),
      nothingFor(paceDurationMinutes minutes),
      rampUsers(scenarioVU) over (rampDurationMinutes minutes),
      nothingFor(paceDurationMinutes minutes),
      rampUsers(scenarioVU / 2) over (rampDurationMinutes minutes),
      nothingFor(paceDurationMinutes minutes),
      rampUsers(scenarioVU / 2) over (rampDurationMinutes minutes),
      nothingFor(paceDurationMinutes minutes),
      rampUsers(scenarioVU / 2) over (rampDurationMinutes minutes),
      nothingFor(paceDurationMinutes minutes)
    )


  def httpProtocol = http
    .warmUp("http://www.google.com")
    .baseURL(baseUri)
    .extraInfoExtractor(extraInfo => List(extraInfo.request))
    //     .proxy(Proxy("localhost", 8889)
    //     .httpsPort(8889))
    .inferHtmlResources(
    WhiteList(Seq("http[s]?://.*/sports.staticcache.org/.*" , "http[s]?://.*/sports2.staticcache.org/.*")),
    BlackList("http[s]?://.*/metrics.testsite.com.*")
  )
    .silentResources
    .disableClientSharing
}

