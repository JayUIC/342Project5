package sample;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    private Socket serverConnection;


    private Thread listener = new serverComm();

    private PrintWriter out;
    private Scanner in;

    private String usrname;


    Stage mainStage = new Stage();

    Client(Socket s){
        serverConnection = s;


        try{
            in = new Scanner(serverConnection.getInputStream());
            out = new PrintWriter(serverConnection.getOutputStream(), true);
        }
        catch (IOException e){

        }
    }



    public void loadLobby() {
        Pane lobbyPane = new Pane();
        ScrollPane lobbyContainer = new ScrollPane();
        VBox lobbyList = new VBox();
        Scene lobbyScene = new Scene(lobbyPane, 750, 750);



    }


    class serverComm extends Thread {
        public  void run () {
            while (in.hasNextLine()){
                String inputLine = in.nextLine();
                parseInput(inputLine);
            }
        }
        private void parseInput(String input){
            String delim = ";";
            String[] pieces = input.split(delim);

            int methodCode = Integer.parseInt(pieces[0]);

        }
    }
}
