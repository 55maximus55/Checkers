package ru.codemonkeystudio.checkers

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import io.socket.client.IO
import io.socket.client.Socket

class GDXGame : Game() {
    lateinit var socket: Socket

    override fun create() {
        connectServer()
        configEvents()
    }

    private fun configEvents() {
        socket.on(Socket.EVENT_CONNECT) {
            Gdx.app.log("SocketIO", "Connected")
        }
    }

    fun connectServer() {
        try {
            socket = IO.socket("http://217.25.223.86:34197")
            socket.connect()
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        super.render()
    }
}
