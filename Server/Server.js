var express = require('express');
var app = express();
var server = require('http').Server(app);
var io = require('socket.io').listen(server);

app.get('/',function(req,res){
  res.sendFile('/Users/jaynimkar/Desktop/Server/index.html');
});

server.listen(8081);
