import {Keys} from "./../keys.js";



export class LevelOne extends Phaser.Scene
{
    constructor()
    {
        super({key: Keys.Scenes.LevelOne});

    }

    Medic_Sprite;
    enemies;
     
    init()
    {

    }

    preload()
    {
        this.load.image(Keys.Images.Fighter, '../../assets/Fighter/Fighter.png');
        this.load.image(Keys.Images.Wizard, '../../assets/Wizard/Wizard.png');
        this.load.image(Keys.Images.Ranger, '../../assets/Ranger/Ranger.png');
        this.load.image(Keys.Images.Medic, '../../assets/Medic/Medic.jpg');
        this.load.image(Keys.Images.Skeleton, '../../assets/Skeleton/Skeleton.png');

    }

    create()
    {
        console.log("create called");
        let MyChar = 1; // temp

        let Fighter_Sprite = this.add.sprite(400,400, Keys.Images.Fighter);
        Fighter_Sprite.setScale(2.5,2.5);

        let Ranger_Sprite = this.add.sprite(500,400, Keys.Images.Ranger);
        Ranger_Sprite.setScale(0.5,0.5);

        let Wizard_Sprite = this.add.sprite(400,500, Keys.Images.Wizard);
        Wizard_Sprite.setScale(2.5,2.5);

        this.Medic_Sprite = this.add.sprite(500,500, Keys.Images.Medic);
        this.Medic_Sprite.setScale(2.5,2.5);

        let Enemy_Sprite = this.add.sprite(0,0,Keys.Images.Skeleton);

        


    }

    
    update()
    {
        let WKey = this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.W);
        let AKey = this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.A);
        let SKey = this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.S);
        let DKey = this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.D);
        let SpaceKey = this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.SPACEBAR);


        if(WKey.isDown)
        {
            if(DKey.isDown)
            {
                // Move up and Right
                //Character_Array[MyID].State = M_UR;
                
            }
            else if (AKey.isDown)
            {
                //Move up and Left
                //Character_Array[MyID].State = M_UL;
            }
            else
            {
                //Move up
                //Character_Array[MyID].State = M_U;
            }
        }
        else if(SKey.isDown)
        {
            if(DKey.isDown)
            {
                // Move down and Right
                //Character_Array[MyID].State = M_DR;
            }
            else if (AKey.isDown)
            {
                //Move down and Left
                //Character_Array[MyID].State = M_DL;
            }
            else
            {
                //Move down
                //Character_Array[MyID].State = M_D;
            }
        }
        else if(AKey.isDown)
        {
            // Move Left
            //Character_Array[MyID].State = "M_R";
        }
        else if(DKey.isDown)
        {
            // Move Right
            //Character_Array[MyID].State= "M_L";
            
        }
        
        if(SpaceKey.isDown)
        {
            //TODO Attack
            //Character_Array[MyID].Attacking = true;
        }
    }

    render()
    {

    }
}