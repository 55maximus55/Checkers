package ru.codemonkeystudio.checkers.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FitViewport
import ru.codemonkeystudio.checkers.GDXGame
import ru.codemonkeystudio.checkers.tools.CMSTextInputListener

class LoginScreen(game: GDXGame) : Screen {
    var game: GDXGame = game

    //cam & stage
    var camera = OrthographicCamera()
    var stage = Stage(FitViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat(), camera))

    var inp = CMSTextInputListener()
    lateinit var textField: TextField

    override fun show() {
        //set input
        Gdx.input.inputProcessor = stage

        //init table
        val table = Table().apply {
            center()
            setFillParent(true)
        }

        //init styles
        val textFieldStyle = TextField.TextFieldStyle().apply {
            font = BitmapFont()
            fontColor = Color.GRAY
            focusedFontColor = Color.WHITE
        }

        //init
        textField = TextField("", textFieldStyle).apply {
            setAlignment(Align.center)
            messageText = "enter name"
            maxLength = 20
            isDisabled = true
            addListener(object : ClickListener() {
                override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                    super.touchUp(event, x, y, pointer, button)
                    Gdx.input.getTextInput(inp, "Title", textField.text, "")
                }
            })
        }

        table.add(textField)

        stage.addActor(table)
    }

    override fun render(delta: Float) {
        camera.update()

        stage.act()
        stage.setDebugAll(true)
        stage.draw()

        if (inp.a) {
            textField.text = inp.input
            inp.a = false
        }
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