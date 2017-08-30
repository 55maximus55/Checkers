package ru.codemonkeystudio.checkers

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONArray
import org.json.JSONObject
import ru.codemonkeystudio.checkers.objects.Room
import ru.codemonkeystudio.checkers.screens.SplashScreen

class GDXGame : Game() {
    lateinit var socket: Socket
    var gameList = ArrayList<String>()

    var roomID = "-1"
    var rooms = HashMap<String, Room>()

    var skin = Skin()
    lateinit var uiAtlas : TextureAtlas

    override fun create() {
        uiAtlas = TextureAtlas(Gdx.files.internal ("textures/textureUI.pack"))
        skin.addRegions(uiAtlas)

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
            for (i in 0 until objects.length()) {
                gameList.add(objects[i].toString())
            }
            Gdx.app.log("SocketIO", "GameList")
            for (i in 0 until gameList.size) {
                Gdx.app.log(i.toString(), gameList[i])
            }
        }
        socket.on("roomList") { args ->
            val objects = args[0] as JSONArray
            for (i in 0 until objects.length()) {
                rooms.put(objects.getJSONObject(i).getString("id"), Room(objects.getJSONObject(i).getString("game")))
            }
        }

        socket.on("createRoom") { args ->
            val data = args[0] as JSONObject
            rooms.put(data.getString("roomID"), Room(data.getString("game")))
        }
    }

    private fun connectServer() {
        socket = IO.socket("http://localhost:34197")
        socket.connect()
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        super.render()
    }
}
