package org.rpnkv.practice.iv.quadcode.core

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

import scala.collection.immutable

abstract class GenericStorageTest extends AnyFunSuite {

  private val GENERIC_SIZE = 5000
  private val KEY_SET_SIZE: Int = GENERIC_SIZE
  private val VALUE_SET_SIZE: Int = GENERIC_SIZE


  val storage: Storage

  test("fill storage with some values and get proper result"){
    for(key <- keySetSupplier){//fill storage with test data
      for(value <- valueSetSupplier){
        storage.put(key, value)
      }
    }

    storage.reports().foreach(reportEntry => reportEntry._2 shouldEqual GENERIC_SIZE)
  }


  def keySetSupplier: immutable.Seq[String] = {
    (1 to KEY_SET_SIZE).map(_.toString)
  }

  def valueSetSupplier: immutable.IndexedSeq[Long] = {
    (1 to VALUE_SET_SIZE).map(_.longValue())
  }

}
