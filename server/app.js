var app = require('express')();
var server = require('http').Server(app);
var io = require('socket.io')(server);

server.listen(34197, function() {
    console.log("Server started");
});

io.on('connection', function(socket) {
    console.log("Player connected (" + socket.id + ")");



    socket.on('disconnect', function() {
        console.log("Player disconnected (" + socket.id + ")");
    });
});