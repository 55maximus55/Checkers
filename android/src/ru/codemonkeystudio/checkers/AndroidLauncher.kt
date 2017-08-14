package ru.codemonkeystudio.checkers

import android.os.Bundle

import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import ru.codemonkeystudio.checkers.GDXGame

class AndroidLauncher : AndroidApplication() {
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        val config = AndroidApplicationConfiguration()
        initialize(GDXGame(), config)
    }
}
