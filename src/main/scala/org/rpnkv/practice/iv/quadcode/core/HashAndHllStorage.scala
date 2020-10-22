package org.rpnkv.practice.iv.quadcode.core

import com.google.common.hash.Hashing
import net.agkn.hll.HLL

import scala.collection.mutable

/**
 * Uses HashMap for 1st layer access and HyperLogLog storage for low-lever access (keeping number fo distinct records).
 * @param hashFunction
 * @param hllFactory
 * @param map
 */
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

  override def reports(): Map[String, Int] = map.map(tuple => tuple._1 -> tuple._2.cardinality.intValue).toMap

}

object HashAndHllStorage {

  private val hashFunction = Hashing.murmur3_128

  def apply(): HashAndHllStorage = {
    new HashAndHllStorage(
      HashAndHllStorage.hashLong,
      () => new HLL(14, 5),
      {
        val builder = mutable.Map.newBuilder[String, HLL]
        builder.sizeHint(100000)
        builder.result()
      }
    )
  }
  def hashLong(long: Long): Long = {
    hashFunction.newHasher.putLong(long).hash().asLong()
  }
}
