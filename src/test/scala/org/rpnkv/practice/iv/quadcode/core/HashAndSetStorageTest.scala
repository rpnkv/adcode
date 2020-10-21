package org.rpnkv.practice.iv.quadcode.core

import org.scalatest.funsuite.AnyFunSuite


class HashAndSetStorageTest extends GenericStorageTest {


  override val genericSize: Int = 10000
  override val storage: Storage = new HashAndSetStorage()
}
