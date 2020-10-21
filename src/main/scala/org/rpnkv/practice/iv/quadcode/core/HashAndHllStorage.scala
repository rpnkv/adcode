package org.rpnkv.practice.iv.quadcode.core

import com.google.common.hash.Hashing
import net.agkn.hll.HLL

import scala.collection.mutable

class HashAndHllStorage(hashFunction: Long => Long, hllFactory: () => HLL, map: mutable.Map[String, HLL]) extends Storage {

  override def put(name: String, value: Long): Unit = {
    val hashedValue = value.hashCode()

    map.get(name) match {
      case Some(hll) => hll.addRaw(hashedValue)
      case None =>
        val hll = hllFactory()
        hll.addRaw(hashedValue)
        map.put(name, hll)
    }
  }

  override def reports(): Seq[(String, Int)] = map.map(tuple => tuple._1 -> tuple._2.cardinality.intValue).toSeq

}

object HashAndHllStorage {

  private val hashFunction = Hashing.murmur3_128

  def apply(): HashAndHllStorage = {
    //new HashAndHllStorage(hashLong)
    ???
  }

 /* val a = Random.nextLong()
  val b = Random.nextLong()
  val c = Random.nextLong()*/

  def hashLong(long: Long): Long = {
    hashFunction.newHasher.putLong(long).hash().asLong()
   /* val low = long
    val high = (long >>> 32)
    ((a * low + b * high + c) >>> 32)*/
  }
}
