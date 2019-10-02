package com.simulations

import scala.concurrent.duration._
import io.gatling.core.Predef._
import com.scenarios.SportsLogin._

/**
  * Created by Juri on 02/10/2019.
  */

class SportsLogin  extends Simulation with SimulationSettingsTrait{
  if (singleRun) {
    setUp(sportsLogin.inject(atOnceUsers(vU)).protocols(httpProtocol)).maxDuration(runDurationMinutes minutes)
  }
  else {
    setUp(sportsLogin.inject(rampProfile(vU)).protocols(httpProtocol)).maxDuration(runDurationMinutes minutes)
  }
}
