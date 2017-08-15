var app = require('express')();
var server = require('http').Server(app);
var io = require('socket.io')(server);

var games = ["tictactoe", "checkers", "chess"]
var rooms = []
var players = []

// запуск сервера
server.listen(34197, function() {
    console.log("Server started");
});

//подключение игрока к серверу
io.on('connection', function(socket) {
    console.log("Player connected (" + socket.id + ")");
    socket.broadcast.emit('playerConnected', { id: socket.id });
    players.push(new player(socket.id));

    //отправка списка игр игроку
    socket.emit('gameList', games);
    //отправка списка комнат игроку
    socket.emit('roomList', rooms);

    //создание комнаты
    socket.on('createRoom', function(data) {
        for (var i = 0; i < players.length; i++) {
            if (players[i] == socket.id) {
                //проверка на участие в других комнатах
                if (players[i].room == -1) {
                    //проверка на существование игрового режима
                    var g = false;
                    for (var i = 0; i < games.length; i++) {
                        if (data.game == games[i]) {
                            g = true;
                        }
                    }
                    if (g) {
                        //создание комнаты и сообщение об этом игрокам
                        rooms.push(new room(data.game, socket.id));
                        socket.emit('createRoomSuccess', { roomID: socket.id });
                        socket.broadcast.emit('createRoom', { roomID: socket.id, game: data.game });
                        console.log("Created Room (" + socket.id + ")");
                    }
                    else {
                        socket.emit('createRoomError', { reason: "Invalid game name" });
                    }
                }
                else {
                    socket.emit('createRoomError', { reason: "Leave another room before create another" });
                }
            }
        }
    });

    //вход в комнату
    socket.on('joinRoom', function(data) {
        
    });

    //выход из комнаты
    socket.on('leaveRoom', function(data) {

    });

    //удаление комнаты
    socket.on('deleteRoom', function(data) {

    });

    //ход игрока
    socket.on('playerMove', function(data) {

    });

    //отключение игрока от сервера
    socket.on('disconnect', function() {
        //оповещение об отключении
        console.log("Player disconnected (" + socket.id + ")");
        socket.broadcast.emit('playerDisconnected', { id: socket.id });
        //удаление отключившегося игрока
        for (var i = 0; i < players.length; i++) {
            if (players[i].id == socket.id) {
                if (players[i].room != -1) {
                    for (var j = 0; j < rooms.length; j++) {
                        if (players[i].room == rooms[j].id) {
                            for (var k = 0; k < rooms[j].players.length; k++) {
                                if (players[i].id == rooms[j].players[k]) {
                                    rooms[j].players.splice(k, 1);
                                }
                            }
                        }
                    }
                }
                //TODO("Удалить комнату если это был последний игрок")
                players.splice(i, 1);
            }
        }
    });
});

function player(id) {
    this.id = id;
    this.room = -1;
}

function room(game, id) {
    this.game = game;
    this.id = id;
    this.players = [];
    this.players.push(id);
}

function board(game) {
    this.game = game;
    this.pieces = [];
}

//Tic Tac Toe
function tictactoeMove(data) {
    
}
function tictactoePiece(x, y, zero) {
    this.x = x;
    this.y = y;
    this.zero = zero;
}

//Checkers
function checkersPiece(x, y, white) {
    this.x = x;
    this.y = y;
    this.white = white;
    this.king = false;
}