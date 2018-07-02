package com.greyblocks.gatekeeper

interface UserDetailMapper<T> {

    fun getUser() :T
    fun saveUser(user:T)
}