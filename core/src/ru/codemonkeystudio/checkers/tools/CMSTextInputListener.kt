package ru.codemonkeystudio.checkers.tools

import com.badlogic.gdx.Input

class CMSTextInputListener : Input.TextInputListener {
    var input = ""
    var a = false

    override fun input(text: String?) {
        input = text!!
        a = true
    }

    override fun canceled() {
    }
}