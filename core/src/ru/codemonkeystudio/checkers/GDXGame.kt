package ru.codemonkeystudio.checkers

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.client.SocketIOException
import org.json.JSONArray
import org.json.JSONException
import ru.codemonkeystudio.checkers.screens.SplashScreen


class GDXGame : Game() {
    lateinit var socket: Socket
    var gameList = ArrayList<String>()

    override fun create() {
        connectServer()
        configEvents()

        setScreen(SplashScreen(this))
    }

    private fun configEvents() {
        socket.on(Socket.EVENT_CONNECT) {
            Gdx.app.log("SocketIO", "Connected (${socket.id()})")
        }
        socket.on("gameList") { args ->
            val objects = args[0] as JSONArray
            try {
                for (i in 0..objects.length() - 1) {
                    gameList.add(objects[i].toString())
                }
            } catch (e: JSONException) {
                Gdx.app.log("SocketIO", "Error getting gameList")
            }
        }
    }

    fun connectServer() {
        try {
            socket = IO.socket("http://217.25.223.86:34197")
            socket.connect()
        } catch (e : SocketIOException) {
            e.printStackTrace()
        }
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        super.render()
    }
}
