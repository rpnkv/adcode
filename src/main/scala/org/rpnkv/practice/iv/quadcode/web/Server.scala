package org.rpnkv.practice.iv.quadcode.web

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes.OK
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import org.rpnkv.practice.iv.quadcode.core.Storage
import spray.json.DefaultJsonProtocol._

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

class Server(storage: Storage){
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
        get{
          complete(OK, storage.reports())
        }
      }
}

object Server{
  def main(args: Array[String]): Unit = {

    implicit val ec: ExecutionContextExecutor = scala.concurrent.ExecutionContext.global
    implicit val system: ActorSystem = ActorSystem("my-system", defaultExecutionContext = Option(ec))
    // needed for the future flatMap/onComplete in the end

    val server = new Server(null)

    val bindingFuture = Http().newServerAt("localhost", 8080).bind(server.route)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}
