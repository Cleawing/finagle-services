package com.cleawing.finagle.http

import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.httpx.{Http, Status, Request, Response}
import com.twitter.finagle.Service

import scala.concurrent.{Promise, Future}
import scalaz.{\/, -\/, \/-}

trait Client {
  val host: String
  val port: Int
  val tlsOn: Boolean
  val tlsSupport: Option[TLSSupport]

  lazy val client: Service[Request, Response] = {
    val builder = ClientBuilder()
      .codec(Http())
      .hosts(s"$host:$port")
      .hostConnectionLimit(1)
      .failFast(false)

    ((tlsOn, tlsSupport) match {
      case (true, Some(tls)) => builder.tls(tls.getSSLContext)
      case _ => builder
    }).build
  }

  def simpleGet(uri: String) : Future[Client.Failure \/ Client.Response] = {
    doSimpleRequest(Request(uri))
  }

  def close(): Future[Unit] =  {
    val promise = Promise[Unit]()
    client.close().onSuccess(_ => promise.success(())).onFailure(ex => promise.failure(ex))
    promise.future
  }

  private def doSimpleRequest(request: Request) : Future[Client.Failure \/ Client.Response] = {
    val promise = Promise[Client.Failure \/ Client.Response]()

    client(request).onSuccess { resp =>
      resp.status match {
        case Status.Ok => promise.success(\/-(Client.Success(resp.status, resp.getContentString())))
        case other => promise.success(\/-(Client.Error(resp.status, resp.getContentString())))
      }
    }.onFailure(ex => promise.success(-\/(Client.Failure(ex))))

    promise.future
  }
}

object Client {
  sealed trait Response {
    val status: Status
    val body: String
  }

  case class Success(status: Status, body: String) extends Response
  case class Error(status: Status, body: String) extends Response
  case class Failure(cause: Throwable)
}
