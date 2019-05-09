import {Keys} from "./../keys.js";



export class Player
{
    PlayerID;
    Character;
    X_Pos;
    Y_Pos;
    Health;
    Speed;

    States = {
        IDLE: "idle",
        M_L: "Left",
        M_R: "Right",
        M_U: "Up",
        M_D: "Down",
        M_UL: "Up_Left",
        M_UR: "Up_Right",
        M_DR: "Down_Right",
        M_DL: "Down_Left"
    };

    Current_State;
    Attacking;

    constructor(ID,Char,X,Y)
    {
        PlayerID = ID;
        Character = Char;
        X_Pos = X;
        Y_Pos = Y;
        Health = 100;
        Speed = 1;
        Current_State = States.IDLE;
        Attacking = false;
    }
}

