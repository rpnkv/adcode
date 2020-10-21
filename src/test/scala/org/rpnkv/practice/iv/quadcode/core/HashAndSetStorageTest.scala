package org.rpnkv.practice.iv.quadcode.core

import org.scalatest.funsuite.AnyFunSuite


class HashAndSetStorageTest extends GenericStorageTest {


  override val storage: Storage = new HashAndSetStorage()
}
