package org.rpnkv.practice.iv.quadcode.core

import net.agkn.hll.HLL

import scala.collection.mutable

class HashHllStorageTest extends GenericStorageTest {

  override val storage: Storage = new HashAndHllStorage(
    HashAndHllStorage.hashLong,
    () => new HLL(14, 5),
    {
      val builder = mutable.Map.newBuilder[String, HLL]
      builder.sizeHint(100000)
      builder.result()
    }
  )
  override def genericSize: Int = 10000
}
