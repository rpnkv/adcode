package org.rpnkv.practice.iv.quadcode.core

import java.util.Random

import scala.collection.mutable.{Map => MutableMap}

/** A helper class for performing CountMinSketch. */
private class CountMinSketchCounter(
                                     depth: Int,
                                     width: Int,
                                     hashFunctions: Seq[(Int, Int)]) extends Serializable {
  val counters: Seq[MutableMap[Int, Long]] = Seq.tabulate(depth)(i =>
    MutableMap.empty[Int, Long])
  val MOD: Int = 2147483647
  val HL: Int = 31

  def query(item: Long): Long = {
    (0 until depth).map { n =>
      val hashKey = hashFunctions(n)
      val hashValue = hash31(hashKey._1, hashKey._2, item)
      val idx = hashValue % width
      if (counters(n).contains(idx)) {
        counters(n)(idx)
      } else {
        0
      }
    }.min
  }

  def update(item: Long, count: Int = 1): Unit = {
    (0 until depth).map { n =>
      val hashKey = hashFunctions(n)
      val hashValue = hash31(hashKey._1, hashKey._2, item)
      val idx = hashValue % width
      add(n, idx, count)
    }
  }

  private def initHash(): Seq[(Int, Int)] = {
    val random = new Random()
    (0 until depth).map { n =>
      val hash1 = random.nextInt() & MOD
      val hash2 = random.nextInt() & MOD
      (hash1, hash2)
    }.toSeq
  }

  /**
   * Calculate the hashing value for updating counters.
   * Ported from http://www.cs.rutgers.edu/~muthu/prng.c.
   */
  private def hash31(a: Long, b: Long, c: Long): Int = {
    var result: Long = (a * c) + b
    result = ((result >> HL) + result) & MOD
    result.toInt
  }

  /**
   * Add an event count to the counters.
   */
  def add(row: Int, key: Int, count: Long): this.type = {
    if (counters(row).contains(key)) {
      counters(row)(key) += count
    } else {
      counters(row)(key) = count
    }
    this
  }

  /**
   * Merge two counters.
   * @param other The counters containing the counts for that partition
   */
  def merge(other: CountMinSketchCounter): this.type = {
    var i = 0
    other.counters.foreach { c =>
      c.foreach { case (k, v) =>
        add(i, k, v)
      }
      i += 1
    }
    this
  }
}
