package org.rpnkv.practice.iv.quadcode.core

import com.google.common.hash.Hashing
import net.agkn.hll.HLL

import scala.collection.mutable

class HashAndHllStorage(hashFunction: Long => Long, hllFactory: () => HLL, map: mutable.Map[String, HLL]) extends Storage {

  override def put(name: String, value: Long): Unit = {
    val hashedValue = hashFunction(value)

    map.get(name) match {
      case Some(hll) => hll.addRaw(hashedValue)
      case None =>
        val hll = hllFactory()
        hll.addRaw(hashedValue)
        map.put(name, hll)
    }
  }

  override def reports(): Seq[(String, Int)] = ???

}

object HashAndHllStorage {

  private val hashFunction = Hashing.murmur3_128

  def apply(): HashAndHllStorage = {
    //new HashAndHllStorage(hashLong)
    ???
  }

  def hashLong(long: Long): Long = {
    hashFunction.newHasher.putLong(long).hash().asLong()
  }
}
