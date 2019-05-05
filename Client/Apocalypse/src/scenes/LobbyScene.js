import {Keys} from "./../keys.js";

export class LobbyScene extends Phaser.Scene {
    constructor () {
        super({
            key: Keys.Scenes.Lobby
        });
    }

    init () {

    }

    preload () {
        this.load.image(Keys.Images.Lobby, '../../assets/maxresdefault.jpg');
        //this.load.image(Key.Images.PlayButton, '../../assets/playButton.jpg');
    }

    create() {
        this.add.image(0,0, Keys.Images.Lobby).setOrigin(0);
        //let playButton = this.add.image(400, 300, Key.Images.PlayButton).setOrigin(0);
        //this.scene.add(Key.Scenes.Lobby, Lobby);
        

       /* playButton.on("pointerdown", () => {
            this.scene.start(Key.Scenes.Lobby);
        }); */

    }
}