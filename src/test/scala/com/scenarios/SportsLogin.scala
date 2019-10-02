package com.scenarios

import com.helpers.Common
import com.requestObjects.{HomePage, Login}
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by Juri on 02/10/2019.
  */

object SportsLogin  {

  val ualistfeeder = csv("UADesktop.csv", escapeChar = '\\')
  val usersfeeder = csv("UserAccounts" + Common.environmentUnderTest.toUpperCase + ".csv")

  val sportsLogin =
    scenario("Desktop Login")
      .forever {
        exec(session => session.reset)
          .exec(flushHttpCache)
          .exec(flushCookieJar)
          .exec(flushSessionCookies)
          .feed(ualistfeeder.random.circular)
          .feed(usersfeeder.random.circular)
          .exec(HomePage.get, Login.login)
      }
}

