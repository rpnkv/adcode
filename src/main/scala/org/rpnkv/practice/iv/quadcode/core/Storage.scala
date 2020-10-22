package org.rpnkv.practice.iv.quadcode.core

/**
 * Describes generic key-value storage contract.
 */
trait Storage {

  def put(name: String, value: Long): Unit

  def reports(): Map[String, Int]

}
