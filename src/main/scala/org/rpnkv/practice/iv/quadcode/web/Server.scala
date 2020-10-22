package org.rpnkv.practice.iv.quadcode.web

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes.{BadRequest, OK}
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

  import Server.{EVENT_NAME, VALUE}

  val route: Route =
    path("data") {
      get {
        entity(as[Map[String, String]]) { map =>
          map.get(EVENT_NAME) match {
            case Some(eventName) =>
              map.get(VALUE) match {
                case Some(value) =>
                  storage.put(eventName, value.toLong)
                  complete(OK)
                case None => complete(BadRequest, s"field $VALUE not fould in json")
              }
            case None => complete(BadRequest, s"field $EVENT_NAME not found in json")
          }
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

  final val EVENT_NAME = "event_name"
  final val VALUE = "value";


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
