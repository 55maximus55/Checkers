var app = require('express')();
var server = require('http').Server(app);
var io = require('socket.io')(server);

var games = ["checkers", "chess"]
var players = []

// запуск сервера
server.listen(34197, function() {
    console.log("Server started");
});

//подключение игрока к серверу
io.on('connection', function(socket) {
    console.log("Player connected (" + socket.id + ")");

    //отправка списка игр игроку
    socket.emit('gameList', games);



    //отключение игрока от сервера
    socket.on('disconnect', function() {
        console.log("Player disconnected (" + socket.id + ")");
        socket.broadcast.emit('playerDisconnected', { id: socket.id });

    });
});

function player(id) {
    this.id = id;
    this.game = -1;
    this.room = -1;
}

function room(game, id) {
    this.game = game;
    this.id = id;
    this.players = [];
}