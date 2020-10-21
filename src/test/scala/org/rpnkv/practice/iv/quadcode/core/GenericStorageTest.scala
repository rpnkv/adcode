package org.rpnkv.practice.iv.quadcode.core

import org.scalactic.TripleEqualsSupport.Spread
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

import scala.collection.immutable

abstract class GenericStorageTest extends AnyFunSuite {

  def genericSize: Int

  val storage: Storage

  test("fill storage with some values and get proper result"){
    for(key <- keySetSupplier){//fill storage with test data
      for(value <- valueSetSupplier){
        storage.put(key, value)
      }
    }

    storage.reports().foreach(reportEntry => reportEntry._2.doubleValue() shouldBe Spread(genericSize, genericSize * 0.01))

    println()
  }

  def keySetSupplier: immutable.Seq[String] = {
    (1 to genericSize).map(_.toString)
  }

  def valueSetSupplier: immutable.IndexedSeq[Long] = {
    (1 to genericSize).map(_.longValue())
  }

}
