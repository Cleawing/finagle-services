package com.cleawing.finch

import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.httpx.{Http, Status, Request}
import com.twitter.finagle.{ChannelClosedException, ChannelWriteException, Service, Httpx}
import io.finch.{HttpResponse, HttpRequest}

import scala.concurrent.{Promise, Future}

trait HttpClient {
  val host: String
  val port: Int
  val tlsOn: Boolean
  val tlsSupport: Option[TLSSupport]

  lazy val client: Service[HttpRequest, HttpResponse] = (tlsOn, tlsSupport) match {
    case (true, Some(tls)) => ClientBuilder()
      .codec(Http())
      .hosts(s"$host:$port")
      .tls(tls.getSSLContext)
      .hostConnectionLimit(1)
      .build()
    case _ => Httpx.newClient(s"$host:$port").toService
  }

  def simpleGet(uri: String) : Future[Either[HttpClient.Failure, HttpClient.Response]] = {
    doSimpleRequest(Request(uri))
  }

  private def doSimpleRequest(request: HttpRequest) : Future[Either[HttpClient.Failure, HttpClient.Response]] = {
    val promise = Promise[Either[HttpClient.Failure, HttpClient.Response]]()

    client(request).onSuccess { resp =>
      resp.status match {
        case Status.Ok => promise.success(Right(HttpClient.Success(resp.status, resp.getContentString())))
        case other => promise.success(Right(HttpClient.Error(resp.status, resp.getContentString())))
      }
    }.onFailure {
      case ex @ (_: ChannelWriteException | _: ChannelClosedException) => promise.success(Left(HttpClient.Failure(ex)))
      case t: Throwable => promise.failure(t)
    }

    promise.future
  }
}

object HttpClient {
  sealed trait Response {
    val status: Status
    val body: String
  }

  case class Success(status: Status, body: String) extends Response
  case class Error(status: Status, body: String) extends Response
  case class Failure(cause: Throwable)
}
