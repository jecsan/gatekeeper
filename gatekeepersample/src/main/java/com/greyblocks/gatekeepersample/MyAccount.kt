package com.greyblocks.gatekeepersample

import com.greyblocks.gatekeeper.Key
import com.greyblocks.gatekeeper.UserAccount

@UserAccount
class MyAccount (@Key("id" ) var id:Long=-1,
                 @Key("name") var displayName:String="")