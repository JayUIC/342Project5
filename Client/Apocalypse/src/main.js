import { StartScene } from './scenes/StartScene.js';

let game = new Phaser.Game({
    width: 800,
    height: 600,
    scene: [
        StartScene
    ]
});