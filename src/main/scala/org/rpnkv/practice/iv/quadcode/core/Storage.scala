package org.rpnkv.practice.iv.quadcode.core

trait Storage {

  def put(name: String, value: Long): Unit

  def reports(): Seq[(String, Int)]

}
