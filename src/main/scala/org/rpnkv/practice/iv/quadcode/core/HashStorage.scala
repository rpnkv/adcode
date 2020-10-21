package org.rpnkv.practice.iv.quadcode.core

import scala.collection.mutable

class HashStorage extends Storage {

  private val map: mutable.Map[String, mutable.Set[Long]] = {
    val mmBuilder = mutable.Map.newBuilder[String, mutable.Set[Long]]
    mmBuilder.sizeHint(1)
    mmBuilder.result()
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
