package org.rpnkv.practice.iv.quadcode.web

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes.OK
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import org.rpnkv.practice.iv.quadcode.core.{HashAndHllStorage, Storage}
import spray.json.DefaultJsonProtocol._

import scala.concurrent.ExecutionContextExecutor

/**
 * API contains 2 endpoints:
 * /data - consuming JSON with name & value like {"name":1215}
 * /report - returns array, containing number of distinct records, associating with each key
 * @param storage
 */
class Server(storage: Storage) {

  val route: Route =
    path("data") {
      get {
        entity(as[(String, Long)]) { tuple =>
          storage.put(tuple._1, tuple._2)
          complete(OK)
        }
      }
    } ~
      path("reports") {
        get {
          complete(OK, storage.reports())
        }
      }
}

object Server {
  def main(args: Array[String]): Unit = {

    implicit val ec: ExecutionContextExecutor = scala.concurrent.ExecutionContext.global
    implicit val system: ActorSystem = ActorSystem("my-system", defaultExecutionContext = Option(ec))

    val server = new Server(HashAndHllStorage.apply())

    Http()
      .newServerAt("localhost", 8080)
      .bind(server.route)
      .onComplete(_ => println(s"Server is running on localhost:8080"))
  }
}
