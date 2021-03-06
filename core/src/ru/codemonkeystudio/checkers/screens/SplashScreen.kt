package ru.codemonkeystudio.checkers.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.FitViewport
import ru.codemonkeystudio.checkers.GDXGame


class SplashScreen (game : GDXGame): Screen{
    var game: GDXGame = game

    //cam & stage
    var camera = OrthographicCamera()
    var stage = Stage(FitViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat(), camera))

    override fun show() {
        //set input
        Gdx.input.inputProcessor = stage

        //init table
        val table = Table().apply {
            center()
            setFillParent(true)
        }

        val labelStyle = Label.LabelStyle().apply {
            font = BitmapFont()
        }

        val label = Label("Connecting to server", labelStyle)

        table.add(label)

        stage.addActor(table)
    }

    override fun render(delta: Float) {
        camera.update()

        stage.act()
        stage.setDebugAll(true)
        stage.draw()

        if (game.socket.connected()) {
            game.screen = LoginScreen(game)
        }
    }

    override fun resume() {
    }

    override fun pause() {
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun hide() {
    }

    override fun dispose() {
    }


}