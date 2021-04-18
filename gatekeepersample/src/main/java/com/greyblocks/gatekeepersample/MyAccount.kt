package com.greyblocks.gatekeepersample

import com.greyblocks.gatekeeper.UserAccount
import kotlinx.serialization.Serializable

@Serializable
@UserAccount
data class MyAccount(var name: String? = null,
                     var id: Long = 0,
                     var ids: List<Long>? = null
)