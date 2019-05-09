var express = require('express');
var app = express();
var path = require('path');
var server = require('http').Server(app);


app.use('/node_modules', express.static(__dirname + '/../node_modules'));
app.use('/assets', express.static(__dirname + '/../assets'));
app.use('/src', express.static(__dirname + '/'));
app.use('/scenes', express.static(__dirname + '/scenes'));



app.use('.', express.static(__dirname));


app.get('/', function(req, res) {
    res.sendFile(path.join(__dirname + '/../index.html'));
});

server.listen(8080, function() {
    console.log("Listening on " + 8080);
});
