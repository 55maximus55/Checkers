package ru.codemonkeystudio.checkers.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import ru.codemonkeystudio.checkers.GDXGame

fun main(args: Array<String>) {
    val cfg = LwjglApplicationConfiguration()
    LwjglApplication(GDXGame(), cfg)
}