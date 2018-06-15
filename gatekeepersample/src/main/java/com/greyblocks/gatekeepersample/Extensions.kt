package com.greyblocks.gatekeepersample

import android.support.design.widget.TextInputLayout


fun TextInputLayout.getText() :String{
    return this.editText?.text.toString()
}