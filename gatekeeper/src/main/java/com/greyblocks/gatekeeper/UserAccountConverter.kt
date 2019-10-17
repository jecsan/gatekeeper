package com.greyblocks.gatekeeper

interface UserAccountConverter<in R : Any, out T : Any> {

    fun getUserAccount(gateKeeper: GateKeeper): T
    fun saveUserAccount(userAccount: R, gateKeeper: GateKeeper)
}