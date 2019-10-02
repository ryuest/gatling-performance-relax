package com.requestObjects

/**
  * Created by Juri on 02/10/2019.
  */

import com.helpers.Common
import io.gatling.core.Predef._
import io.gatling.http.Predef._

object HomePage {

  val baseUri : String = Common.getConfigFromFile("environment.conf", "baseURL")

  val headersAll = Map(
    HttpHeaderNames.Accept -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
  //  HttpHeaderNames.UserAgent -> "${UAStrings}",
    HttpHeaderNames.AcceptEncoding -> "gzip, deflate, sdch",
    HttpHeaderNames.AcceptLanguage -> "en-GB,en;q=0.8,en-US;q=0.6,it;q=0.4,es;q=0.2",
    HttpHeaderNames.CacheControl -> "max-age=0"
  )

  def get =
      exec(http("Navigate to home - ${platform}")
        .get(baseUri)
        .headers(headersAll)
        .check(status.in(200, 304)))
      //TODO: Add cookie assertion here
      //      .check(regex("<title> - The Home of </title>").count.is(1)))
      .pause(1,5)

}
