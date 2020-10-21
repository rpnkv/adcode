package org.rpnkv.practice.iv.quadcode.core


class HashAndSetStorageTest extends GenericStorageTest {

  override val genericSize: Int = 100000
  override val storage: Storage = new HashAndSetStorage()
}
