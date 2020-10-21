package org.rpnkv.practice.iv.quadcode.core

import scala.collection.mutable

/**
 *  Uses HashMap for 1st layer access and and HashSet for 2nd layer logic (keeping number of distinct records).
 */
class HashAndSetStorage extends Storage {

  var map: mutable.Map[String, mutable.Set[Long]] = {
    val builder = mutable.Map.newBuilder[String, mutable.Set[Long]]
    builder.sizeHint(100000)
    builder.result()
  }

  override def put(name: String, value: Long): Unit = {
    map.get(name) match {
      case Some(set) => set.add(value)
      case None => map.put(name, mutable.HashSet(value))
    }
  }

  override def reports(): Seq[(String, Int)] = {
    map.map(entry => entry._1 -> entry._2.size).toSeq
  }

}
