package org.rpnkv.practice.iv.quadcode.web

import org.mockito.Mockito._
import org.mockito.MockitoSugar
import org.rpnkv.practice.iv.quadcode.core.Storage
import org.scalatest.funsuite.AnyFunSuite

class ServerTest extends AnyFunSuite with MockitoSugar{

  private val storage = mock[Storage]

  test("should accept new entry and pass it to storage"){
    val expectedReportAsMap = Map("name1" -> 245.longValue())


  }

}
