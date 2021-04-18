package com.greyblocks.gatekeeper

import kotlinx.serialization.Serializable

@UserAccount
@Serializable
data class MyAccount(var name: String? = null,
                     var id: Long = 0,
                     var ids: List<Long>? = null,
                     var strings: List<String>? = null,
                     var floats : List<Float>? = null,
                     var ints: List<Int>? = null,
                     var bools: List<Boolean>? = null
)

@UserAccount
@Serializable
data class MyAccount4(var name: String,
                     var id: Long)
