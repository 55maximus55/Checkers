package ru.codemonkeystudio.checkers.screens

import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Stage
import ru.codemonkeystudio.checkers.GDXGame

class RoomScreen(var game: GDXGame) : Screen {
    //cam & stage
    var camera = OrthographicCamera()
    var stage = Stage()

    override fun show() {
    }

    override fun render(delta: Float) {
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun hide() {
    }

    override fun dispose() {
    }
}