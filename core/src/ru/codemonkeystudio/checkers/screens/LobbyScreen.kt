package ru.codemonkeystudio.checkers.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.List
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.FitViewport
import ru.codemonkeystudio.checkers.GDXGame

class LobbyScreen(var game: GDXGame) : Screen {
    //cam & stage
    var camera = OrthographicCamera()
    var stage = Stage(FitViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat(), camera))

    lateinit var roomList: List<String>

    override fun show() {
        Gdx.input.inputProcessor = stage

        //init table
        val table = Table().apply {
            center()
            setFillParent(true)
        }

        val tableTop = Table().apply {
            center()
//            setFillParent(true)
        }

        val listStyle = List.ListStyle().apply {
            font = BitmapFont()
            fontColorSelected = Color.WHITE
            fontColorUnselected = Color.GRAY
            selection = game.skin.getDrawable("knob")
        }

        val textButtonStyle = TextButton.TextButtonStyle().apply {
            font = BitmapFont()
        }

        val buttonCreateRoom = TextButton("Create room", textButtonStyle).apply {
            addListener(object : ClickListener() {
                override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                    super.touchUp(event, x, y, pointer, button)
                    game.screen = CreateRoomScreen(game)
                }
            })
        }

        val buttonLogin = TextButton("Login", textButtonStyle).apply {
            addListener(object : ClickListener() {
                override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                    super.touchUp(event, x, y, pointer, button)
                    game.screen = LoginScreen(game)
                }
            })
        }

        roomList = List(listStyle)
        roomList.apply {
            for (i in 0 until game.rooms.size) {
                items.add(game.rooms.keys.elementAt(i))
            }

        }

        tableTop.add(buttonCreateRoom).pad(16f)
        tableTop.add(buttonLogin)

        table.add(tableTop)
        table.row()
        table.add(roomList)

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

    override fun hide() {
    }

    override fun dispose() {
    }
}