package org.rpnkv.practice.iv.quadcode.web

import akka.http.scaladsl.model.StatusCodes.OK
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.mockito.Mockito._
import org.mockito.MockitoSugar
import org.rpnkv.practice.iv.quadcode.core.Storage
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper


import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.server._
import Directives._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ServerTest extends AnyWordSpec with MockitoSugar with ScalatestRouteTest with Matchers {

  private val storage = mock[Storage]
  private val server = new Server(storage)

  "Server" should {
    "accept new entry and pass it to storage" in {
      val expectedReportAsMap = Map("name1" -> 245.longValue())
      Get("data") ~> server.route ~> check {
        status shouldEqual OK
      }

    }
  }
}
