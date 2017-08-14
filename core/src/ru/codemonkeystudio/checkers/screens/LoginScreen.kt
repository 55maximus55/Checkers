package ru.codemonkeystudio.checkers.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FitViewport
import ru.codemonkeystudio.checkers.GDXGame

class LoginScreen(game: GDXGame) : Screen {
    var game: GDXGame = game

    var camera = OrthographicCamera()
    var stage = Stage(FitViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat(), camera))

    override fun show() {
        Gdx.input.inputProcessor = stage

        val table = Table()
        table.center()
        table.setFillParent(true)

        val textFieldStyle = TextField.TextFieldStyle()
        textFieldStyle.font = BitmapFont()
        textFieldStyle.fontColor = Color.GRAY
        textFieldStyle.focusedFontColor = Color.WHITE

        var textField = TextField("", textFieldStyle)
        textField.setAlignment(Align.center)
        textField.messageText = "enter name"
        textField.maxLength = 20

        table.add(textField)

        stage.addActor(table)
    }

    override fun render(delta: Float) {
        camera.update()

        stage.act()
        stage.setDebugAll(true)
        stage.draw()
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun dispose() {
    }

    override fun hide() {
    }
}