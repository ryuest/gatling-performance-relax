package com.requestObjects

import com.helpers.Common
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import com.helpers.Common._
import org.slf4j.LoggerFactory

object Login {

  val baseURL: String = Common.getConfigFromFile("environment.conf", "baseURL")
  val securebaseURL: String = Common.getConfigFromFile("environment.conf", "securebaseURL")

  def timestamp: Long = System.currentTimeMillis / 1000

  val headersLogin = Map(
    HttpHeaderNames.AcceptEncoding -> "gzip, deflate",
    HttpHeaderNames.Origin -> baseURL,
    HttpHeaderNames.UserAgent -> "${UAStrings}",
    HttpHeaderNames.Accept -> "*/*",
    HttpHeaderNames.ContentType -> "application/x-www-form-urlencoded; charset=UTF-8",
    HttpHeaderNames.AcceptLanguage -> "en-GB,en;q=0.8,en-US;q=0.6,it;q=0.4,es;q=0.2"
  )

  def login =
        exec(http("Login")
          .post(securebaseURL + "/login")
          .formParam("username", "${Username}")
          .formParam("password", "${Password}")
          .formParam("action", "DoLogin")
          .formParam("login_uid", session => timestamp)
          .formParam("target_page", securebaseURL + "/bet/en-gb")
          .formParam("responseType", "json")
          .formParam("remember_me_value", "1")
          .headers(headersLogin)
          .check(status.is(200))
          .check(headerRegex("Set-Cookie", "cust_ssl_login=([^;]+)").exists.saveAs("sslLogin"))
          .check(headerRegex("Set-Cookie", "cust_auth=([^;]+)").exists.saveAs("custAuth"))
          .check(headerRegex("Set-Cookie", "cust_login=([^;]+)").exists.saveAs("custLogin"))
          .check(headerRegex("Set-Cookie", "CSRF_COOKIE=([^;]+)").exists.saveAs("csrfToken")))
        .exec(session => {
          LoggerFactory.getLogger("Account used: " + session("Username").as[String]).info("logging info")
          session
        })
      .doIf(session => !userLoggedIn(session)) {
        exec(session => {
          println("Username: " + session("Username").as[String] + " not logged in successfully.")
          session
        })
      }


}