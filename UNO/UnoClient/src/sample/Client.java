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

    private String numPlayersIn;


    Stage mainStage = new Stage();
    Label lobbyMessage = new Label("");

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
        Label lobbyTitle = new Label("Lobby");
        Scene lobbyScene = new Scene(lobbyPane, 750, 750);

        lobbyPane.setStyle("-fx-background-color: #532fef;");

        mainStage.setTitle("Lobby");
        mainStage.setResizable(false);
        mainStage.show();

        mainStage.setScene(lobbyScene);


        lobbyTitle.setFont(Font.font("Arial",FontWeight.BOLD,25));
        lobbyTitle.setTextFill(Color.BLACK);
        lobbyTitle.relocate(340, 100);


        lobbyMessage.setFont(Font.font("Arial",FontWeight.BOLD,25));
        lobbyMessage.setTextFill(Color.BLACK);
        lobbyMessage.relocate(175, 300);


        lobbyPane.getChildren().addAll(lobbyMessage, lobbyTitle);

        lobbyContainer.setContent(lobbyPane);
    }


    public void player_joined(String playersCon) {
        if (playersCon.equals("1")) {
            lobbyMessage.setText("You're the only one connected to the server.");
        }
        else {
            lobbyMessage.setText("There are " + playersCon + " players connected to the server.");
        }
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

            String methodCode = pieces[0];
            if (methodCode.equals("start game")) {

            }
            else if (methodCode.equals("player joined")) {
                player_joined(pieces[1]);
            }
        }
    }
}
