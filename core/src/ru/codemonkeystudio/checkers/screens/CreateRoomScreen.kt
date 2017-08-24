package ru.codemonkeystudio.checkers.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.FitViewport
import org.json.JSONException
import org.json.JSONObject
import ru.codemonkeystudio.checkers.GDXGame

class CreateRoomScreen(var game: GDXGame) : Screen {
    //cam & stage
    private var camera = OrthographicCamera()
    private var stage = Stage(FitViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat(), camera))

    var setScreen = false
    var gameMode = ""

    override fun show() {
        //set input
        Gdx.input.inputProcessor = stage

        //init table
        val table = Table().apply {
            center()
            setFillParent(true)
        }

        //init styles
        val textButtonStyle = TextButton.TextButtonStyle().apply {
            font = BitmapFont()
        }

        (0 until game.gameList.size).map {
            TextButton(game.gameList[it], textButtonStyle).apply {
                addListener(object : ClickListener() {
                    override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                        super.touchUp(event, x, y, pointer, button)

                        val data = JSONObject()
                        try {
                            data.put("game", game.gameList[it])
                            game.socket.on("createRoomSuccess") { _ ->
                                Gdx.app.log("CreateRoom", "Success")

                                setScreen = true
                                gameMode = game.gameList[it]
                                game.roomID = game.socket.id()

                                game.socket.off("createRoomSuccess")
                                game.socket.off("createRoomError")
                            }
                            game.socket.on("createRoomError") { args ->
                                val dataError = args[0] as JSONObject
                                try {
                                    Gdx.app.log("CreateRoom", dataError.getString("reason"))
                                } catch (e: JSONException) {}

                                game.socket.off("createRoomSuccess")
                                game.socket.off("createRoomError")
                            }
                            game.socket.emit("createRoom", data)
                        } catch (e : JSONException) {}
                    }
                })
            }
        }.forEach {
            table.add(it).pad(16f).row()
        }

        stage.addActor(table)
    }

    override fun render(delta: Float) {
        camera.update()

        stage.act()
        stage.setDebugAll(true)
        stage.draw()

        setScreen()
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

    private fun setScreen () {
        if (setScreen) {
            game.screen = RoomScreen(game, gameMode)
        }
    }
}