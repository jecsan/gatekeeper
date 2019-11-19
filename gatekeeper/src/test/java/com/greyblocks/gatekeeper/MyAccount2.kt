package com.greyblocks.gatekeeper
data class MyAccount2(var name: String? = null,
                      var id: Long = 0,
                      var ids: List<Long>? = null,
                      var strings: List<String>? = null,
                      var floats : List<Float>? = null,
                      var ints: List<Int>? = null,
                      var bools: List<Boolean>? = null
)