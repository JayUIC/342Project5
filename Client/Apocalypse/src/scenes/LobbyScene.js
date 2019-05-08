import {Keys} from "./../keys.js";

export class LobbyScene extends Phaser.Scene 
{
    constructor () {
        super({
            key: Keys.Scenes.Lobby
        });

       
    }

    
    init () {

    }

    preload () {
        //this.load.image(Keys.Images.Lobby, '../../assets/maxresdefault.jpg');

        this.load.image(Keys.Images.Fighter, '../../assets/Fighter/Fighter.png');
        //console.log("fighter loaded");
        this.load.image(Keys.Images.Wizard, '../../assets/Wizard/Wizard.png');
        //console.log("wizard loaded");
        this.load.image(Keys.Images.Ranger, '../../assets/Ranger/Ranger.png');
        //console.log("ranger loaded");
        this.load.image(Keys.Images.Medic, '../../assets/Medic/Medic.jpg');
        //console.log("medic loaded");
    
        //this.load.image(Key.Images.PlayButton, '../../assets/playButton.jpg');
    }

    create() {
        //this.add.image(0,0, Keys.Images.Lobby).setOrigin(0);

        var picked = false;

        // FIGHTER SHEET:: WIDTH : 48.5 HEIGHT : 67.2
        let Fighter_Sprite = this.add.sprite(180,400, Keys.Images.Fighter); 
        Fighter_Sprite.setInteractive();
        Fighter_Sprite.setScale(4,4);
        /*let Fighter_Anim = this.anims.create({
            key:'Fighter_Right',
            frames: this.anims.generateFrameNames(Fighter_Sprite,{start:9,end:16}),
            framerate: 10,
            repeat:-1
        });
        */

        let Medic_Sprite = this.add.sprite(630,400,Keys.Images.Medic);
        Medic_Sprite.setInteractive();
        Medic_Sprite.setScale(4,4);
        //let Medic_Frames = this.anims.generateFrameNames(Keys.Images.Medic);
        
        let Ranger_Sprite = this.add.sprite(350,400,Keys.Images.Ranger);
        Ranger_Sprite.setInteractive();
        Ranger_Sprite.setScale(0.4,0.5);
        //let Ranger_Frames = this.anims.generateFrameNames(Keys.Images.Ranger);

        let Wizard_Sprite = this.add.sprite(500,400,Keys.Images.Wizard);
        Wizard_Sprite.setInteractive();
        Wizard_Sprite.setScale(4,4);
        //let Wizard_Frames = this.anims.generateFrameNames(Keys.Images.Wizard);
        
        let style = {
            fontFamily: 'Courier',
            fontSize: '20px',
            fontStyle: '',
            backgroundColor: '#000',
            color: '#fff',
            stroke: '#fff',
            strokeThickness: 0,
            shadow: {
                offsetX: 0,
                offsetY: 0,
                color: '#000',
                blur: 0,
                stroke: false,
                fill: false
            },
            align: 'center',  // 'left'|'center'|'right'
            maxLines: 0,
            lineSpacing: 0,
            fixedWidth: 0,
            fixedHeight: 0,
            rtl: false,
            wordWrap: {
                width: null,
                callback: null,
                callbackScope: null,
                useAdvancedWrap: false
            }
        };


    

        let Fighter_Button = this.add.text(150,500, 'Fighter', style);
        Fighter_Button.setInteractive();
        Fighter_Button.on('pointerover', () => {/*Fighter_Sprite.anims.play('Fighter_Right',10,true);*/});
        Fighter_Button.on('pointrout', () => {/* Make Animation Stop */});
        Fighter_Button.on('pointerdown', () => {Fighter_Selected()});
        Fighter_Sprite.on('pointerdown',()=>{Fighter_Selected()});

        let Ranger_Button = this.add.text(300,500,'Ranger',style);
        Ranger_Button.setInteractive();
        Ranger_Button.on('pointerover', () => { /* Make Animation Run */});
        Ranger_Button.on('pointrout', () => {/* Make Animation Stop */});
        Ranger_Button.on('pointerdown', () => {Ranger_Selected();});
        Ranger_Sprite.on('pointerdown',()=>{Ranger_Selected();});
        
        let Wizard_Button = this.add.text(450,500,'Wizard',style);
        Wizard_Button.setInteractive();
        Wizard_Button.on('pointerover', () => { /* Make Animation Run */});
        Wizard_Button.on('pointrout', () => {/* Make Animation Stop */});
        Wizard_Button.on('pointerdown', () => {Wizard_Selected()});
        Wizard_Sprite.on('pointerdown',()=>{Wizard_Selected()});

        let Medic_Button = this.add.text(600,500,'Medic',style);
        Medic_Button.setInteractive();
        Medic_Button.on('pointerover', () => { /* Make Animation Run */});
        Medic_Button.on('pointrout', () => {/* Make Animation Stop */});
        Medic_Button.on('pointerdown', () => {Medic_Selected()});
        Medic_Sprite.on('pointerdown',()=>{Medic_Selected()});

        function Fighter_Selected()
        {
            if(picked == false)
            {
                picked = true;
                Ranger_Sprite.setTint(0x575f6b,0x575f6b,0x575f6b,0x575f6b);
                Medic_Sprite.setTint(0x575f6b,0x575f6b,0x575f6b,0x575f6b);
                Wizard_Sprite.setTint(0x575f6b,0x575f6b,0x575f6b,0x575f6b);
                
                console.log("Fighter Selected");
                
            }
            else if(picked == true)
            {
                console.log("You have already selected a character, please wait for other players.");
                //DO NOTHING
            }
            //Ranger_Button.input.enable = false;
            //Ranger_Sprite.input.enable = false;
        }

        function Ranger_Selected()
        {
            if(picked == false)
            {
                picked = true;
                Fighter_Sprite.setTint(0x575f6b,0x575f6b,0x575f6b,0x575f6b);
                Medic_Sprite.setTint(0x575f6b,0x575f6b,0x575f6b,0x575f6b);
                Wizard_Sprite.setTint(0x575f6b,0x575f6b,0x575f6b,0x575f6b);
                
                console.log("Ranger Selected");
                
            }
            else if(picked == true)
            {
                console.log("You have already selected a character, please wait for other players.");
                //DO NOTHING
            }
        }

        function Wizard_Selected()
        {
            if(picked == false)
            {
                picked = true;
                Ranger_Sprite.setTint(0x575f6b,0x575f6b,0x575f6b,0x575f6b);
                Medic_Sprite.setTint(0x575f6b,0x575f6b,0x575f6b,0x575f6b);
                Fighter_Sprite.setTint(0x575f6b,0x575f6b,0x575f6b,0x575f6b);
                
                console.log("Wizard Selected");
                
            }
            else if(picked == true)
            {
                console.log("You have already selected a character, please wait for other players.");
                //DO NOTHING
            }
        }
        
        function Medic_Selected()
        {
            if(picked == false)
            {
                picked = true;
                Ranger_Sprite.setTint(0x575f6b,0x575f6b,0x575f6b,0x575f6b);
                Fighter_Sprite.setTint(0x575f6b,0x575f6b,0x575f6b,0x575f6b);
                Wizard_Sprite.setTint(0x575f6b,0x575f6b,0x575f6b,0x575f6b);
                
                console.log("Medic Selected");
                
            }
            else if(picked == true)
            {
                console.log("You have already selected a character, please wait for other players.");
                //DO NOTHING
            }
        }
    }
}