package ru.codemonkeystudio.checkers

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.client.SocketIOException
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import ru.codemonkeystudio.checkers.objects.Player
import ru.codemonkeystudio.checkers.objects.Room
import ru.codemonkeystudio.checkers.screens.SplashScreen

class GDXGame : Game() {
    lateinit var socket: Socket
    var gameList = ArrayList<String>()

    var roomID = "-1"
    var rooms = HashMap<String, Room>()
    var players = HashMap<String, Player>()

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
            try {
                for (i in 0 until objects.length()) {
                    gameList.add(objects[i].toString())
                }
            } catch (e: JSONException) {
                Gdx.app.log("SocketIO", "Error getting gameList")
            }
            Gdx.app.log("SocketIO", "GameList")
            for (i in 0 until gameList.size) {
                Gdx.app.log(i.toString(), gameList[i])
            }
        }
        socket.on("roomList") { args ->
            val objects = args[0] as JSONArray
            try {
                for (i in 0 until objects.length()) {
                    val players = ArrayList<String>()
                    (0 until objects.getJSONObject(i).getJSONArray("players").length()).mapTo(players) { objects.getJSONObject(it).getJSONArray("players").getString(it) }
                    rooms.put(objects.getJSONObject(i).getString("id"), Room(objects.getJSONObject(i).getString("game"), players))
                }
            } catch (e: JSONException) {
                Gdx.app.log("SocketIO", "Error getting roomList")
            }
        }
        socket.on("playerList") { args ->
            val objects = args[0] as JSONArray
            try {
                for (i in 0 until objects.length()) {
                    players.put(objects.getJSONObject(i).getString("id"), Player(objects.getJSONObject(i).getString("room")))
                }
            }
            catch (e: JSONException) {
                Gdx.app.log("SocketIO", "Error getting playerList")
            }
        }

        socket.on("playerConnected") { args ->
            val data = args[0] as JSONObject
            try {
                players.put(data.getString("id"), Player("-1"))
                Gdx.app.log("SocketIO", "Player connected (${data.getString("id")})")
            }
            catch (e: JSONException) {
                Gdx.app.log("SocketIO", "Error getting connected playerID")
            }
        }
        socket.on("playerDisconnected") { args ->
            val data = args[0] as JSONObject
            try {
                val id = data.getString("id")
                if (players[id]!!.room != "-1") {
                    rooms[players[id]!!.room]!!.players.remove(id)
                }
                players.remove(id)
            }
            catch (e: JSONException) {
                Gdx.app.log("SocketIO", "Error getting disconnected playerID")
            }
        }

        socket.on("createRoom") { args ->
            val data = args[0] as JSONObject
            val players = ArrayList<String>()
            rooms.put(data.getString("roomID"), Room(data.getString("game"), players))
        }
        socket.on("deleteRoom") { args ->
            val data = args[0] as JSONObject
            rooms.remove(data.getString("roomID"))
        }
    }

    private fun connectServer() {
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
