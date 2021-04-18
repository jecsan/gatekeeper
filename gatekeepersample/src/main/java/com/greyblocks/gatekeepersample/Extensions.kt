package com.greyblocks.gatekeepersample

import com.google.android.material.textfield.TextInputLayout
import com.greyblocks.gatekeeper.GateKeeper


fun TextInputLayout.getText() :String{
    return this.editText?.text.toString()

}