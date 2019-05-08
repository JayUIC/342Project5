import {Keys} from "./../keys.js";
import {LobbyScene} from "./LobbyScene.js";
import {LevelOne} from "./LevelOne.js";

export class StartScene extends Phaser.Scene {
    constructor () {
        super({
            key: Keys.Scenes.Start
        })
    }

    init () {

    }

    preload () {
        this.load.image(Keys.Images.Background, '../../assets/background.jpg');
        this.load.image(Keys.Images.PlayButton, '../../assets/start.png');
    }

    create() {
        let bg = this.add.image(0,0, Keys.Images.Background).setOrigin(0).setDepth(0);
       // bg.setDisplaySize(this.parent.game.config.width, this.parent.game.config.height);

        let playButton = this.add.image(180, 300, Keys.Images.PlayButton).setOrigin(0).setDepth(1);
        this.scene.add(Keys.Scenes.Lobby, LobbyScene);
        
        //this.scene.add(Keys.Scenes.LevelOne,LevelOne);
        
        playButton.setScale(0.5, 0.5)
        bg.setScale(1.25, 2);
        playButton.setInteractive();
        
        playButton.on("pointerdown", () => {
            this.scene.start(Keys.Scenes.Lobby);
            //this.scene.start(Keys.Scenes.LevelOne);
        });

    }
}