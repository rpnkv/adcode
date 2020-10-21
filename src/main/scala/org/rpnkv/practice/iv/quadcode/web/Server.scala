package org.rpnkv.practice.iv.quadcode.web

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.GenericMarshallers
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import org.rpnkv.practice.iv.quadcode.core.Storage

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

class Server(storage: Storage){
  val route =
    path("data") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
      }
    } ~
      path("reports") {
        get{
          complete(StatusCodes.OK/*, Map("value1" -> 1482)*/)
        }
      }
}

object Server /*extends GenericMarshallers*/{
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
