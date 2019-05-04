const express = require('express');
const app = express();
const server = require('http').Server(app);
const io = require('socket.io').listen(server);


app.get('/',function(req,res){
  res.sendFile('/Users/jaynimkar/Desktop/Server/index.html');
});

//server.listen(8081);
app.listen(8081, '0.0.0.0');


let numPlayers = 0;
let playersReadyForGame = 0;
let roundNumber = 0;

let playersMadeSelection = 0;
let players = [""];

let socketsArr = [""];

io.on('connection', function(socket){
  socketsArr.add(socket);
  sockets[++numPlayers] = socket;
  players[numPlayers] = {
    player_id: numPlayers,
    xpos: 0,
    ypos: 0,
    health: 100,
    speed: 0,
    current_state: "",
    character_class: ""
  }
  socket.emit('Send_PlayerList', players);
  socket.broadcast.emit('Player_Joined', players[numPlayers]);

});
/* Lobby: Client OppCode Handlers */

io.sockets.on('Exit_Game', function(playerID){
  delete players[playerID];
  io.emit('Player_Left', playerID);
  numPlayers--;
});

io.sockets.on('Class_Selected', function(comm_object){
  playersMadeSelection++;
  players[comm_object.player_id].character_class = comm_object.character_selected;
  let send_object = {
    player_id: comm_object.player_id,
    character_selected: comm_object.character_selected
  };

  io.socketsArr[comm_object.player_id].broadcast.emit('Player_Selected', send_object);

  if (playersMadeSelection == 4){
     playersMadeSelection = 0;
     io.emit('Load_Game');
  }
 });

io.sockets.on('Game_Loaded', function(playerID){
  playersReadyForGame++;
  if (playersReadyForGame == 4){
    io.emit('Begin_Game');
    roundNumber++;
    for (let i = 1; i <= 4; i++){
      players[i].xpos = 50;
      players[i].ypos = 50;
    }
    playersReadyForGame = 0;
  }
});

/* Main Game: Client OppCode Handlers */
io.sockets.on('Start_Round', function(){

});
