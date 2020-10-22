package org.rpnkv.practice.iv.quadcode.web

import akka.http.scaladsl.model.StatusCodes.{BadRequest, OK}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.mockito.MockitoSugar
import org.rpnkv.practice.iv.quadcode.core.Storage
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
import spray.json.{JsArray, JsNumber, JsObject, JsString}

import scala.util.Random

class ServerTest extends AnyWordSpec with MockitoSugar with ScalatestRouteTest with Matchers {

  private val storage = mock[Storage]
  private val server = new Server(storage)

  import org.rpnkv.practice.iv.quadcode.web.Server._

  "Server" when {
    "posted with values" should {
      "accept new when it's correct" in {
        val name = "nameX"
        val value = Random.nextLong()

        Post(
          "/data",
          JsObject(Map(EVENT_NAME -> JsString(name), VALUE -> JsString(value.toString)))
        ) ~> server.route ~> check {
          status shouldEqual OK
          verify(storage).put(name, value)
        }
      }

      s"reject request when there is no $EVENT_NAME field" in {
        Post(
          "/data",
          JsObject(Map(VALUE -> JsString(Random.nextLong().toString)))
        ) ~> server.route ~> check {
          status shouldEqual BadRequest
        }
      }

      s"reject request when there is no $VALUE field" in {
        Post(
          "/data",
          JsObject(Map(EVENT_NAME -> JsString("somename")))
        ) ~> server.route ~> check {
          status shouldEqual BadRequest
        }
      }

      "return storage contents" in {
        val storageContentsAsMap = Map("name1" -> 14, "name2" -> 15)

        when(storage.reports()).thenReturn(storageContentsAsMap)

        Get(
          "/reports"
        ) ~> server.route ~> check {
          status shouldEqual OK
          responseAs[Map[String, Int]] shouldEqual storageContentsAsMap
        }
      }
    }
  }
}
