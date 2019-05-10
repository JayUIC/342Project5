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
import java.util.ArrayList;

public class Client {

    private Socket serverConnection;
    private ArrayList<Card> hand = new ArrayList<>();
    private String[] numCards = {"0", "0", "0", "0"};



    private Thread listener = new serverComm();

    private PrintWriter out;
    private Scanner in;

    private String playerID = "0";


    Stage mainStage = new Stage();
    Label lobbyMessage = new Label("");
    Scene mainScene;
    Pane Background = new Pane();
    Label consoleMessage = new Label();
    VBox gameConsole = new VBox();
    HBox cardTable = new HBox();
    HBox gameTable = new HBox();

    Label p1 = new Label("");
    Label p2 = new Label("");
    Label p3 = new Label("");
    Label p4 = new Label("");


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

    public void all_cards(String[] numberOfCards) {
        p1.setText("Number of Cards Player 1 has: " + numberOfCards[1]);
        p2.setText("Number of Cards Player 2 has: " + numberOfCards[2]);
        p3.setText("Number of Cards Player 3 has: " + numberOfCards[3]);
        p4.setText("Number of Cards Player 4 has: " + numberOfCards[4]);
    }

    public void message(String message) {
        consoleMessage.setText(message);
    }

    public void your_turn() {
        consoleMessage.setText("It's your turn!");
    }

    public void player_joined(String playersCon) {
        if (playersCon.equals("1")) {
            lobbyMessage.setText("You're the only one connected to the server.");
        }
        else {
            lobbyMessage.setText("There are " + playersCon + " players connected to the server.");
        }
    }

    public void send_hand(String[] cards) {
        hand.clear();
        String delim = ",";
        for(int i = 1; i < cards.length; i++) {
            String[] cardElements = cards[i].split(delim);
            Card newCard = new Card(cardElements[0], cardElements[1]);
            hand.add(newCard);
        }
    }

    public void playerCardMessages() {
        p1.setFont(Font.font("Arial",FontWeight.BOLD,14));
        p1.setTextFill(Color.BLACK);

        p2.setFont(Font.font("Arial",FontWeight.BOLD,14));
        p2.setTextFill(Color.BLACK);

        p3.setFont(Font.font("Arial",FontWeight.BOLD,14));
        p3.setTextFill(Color.BLACK);

        p4.setFont(Font.font("Arial",FontWeight.BOLD,14));
        p4.setTextFill(Color.BLACK);

        String[] nums = {"0","0","0","0","0"};//////////////////////////////////////////////////////////////////////////
        all_cards(nums);
        p1.relocate(20, 20);
        p2.relocate(20, 40);
        p3.relocate(20, 60);
        p4.relocate(20, 80);
    }
    public void createGameConsoleGUI(){
        consoleMessage.setFont(Font.font("Arial", FontWeight.BOLD,18));
        consoleMessage.setTextFill(Color.LIGHTGREEN);

        gameConsole.relocate(225,450);
        gameConsole.setMinHeight(75);
        gameConsole.setMinWidth(300);
        gameConsole.setAlignment(Pos.CENTER);

        gameConsole.setStyle("-fx-background-color: #000000");
        gameConsole.getChildren().add(consoleMessage);

        Background.getChildren().addAll(gameConsole, p1, p2, p3, p4);
    }
    public void loadGameScreen(){
        Background.setStyle("-fx-background-color: #532fef;");
        mainScene = new Scene(Background,750,750);
        mainStage.setScene(mainScene);
        mainStage.show();

        createGameConsoleGUI();
        playerCardMessages();

        gameTable.setStyle("-fx-background-color: #3710e8;");
        gameTable.relocate(50,250);
        gameTable.setMinSize(100,100);
        gameTable.setAlignment(Pos.CENTER_LEFT);
    }

    class Card {
        private String name;
        private String color;

        Card(String name, String color) {
            this.name = name;
            this.color = color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public String getColor() {
            return color;
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
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        loadGameScreen();
                        out.println("hand");
                    }
                });
            }
            else if (methodCode.equals("player joined")) {
                if(playerID.equals("0")) {
                    playerID = pieces[1];
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        player_joined(pieces[1]);
                    }
                });
            }
            else if (methodCode.equals("message")) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        message(pieces[1]);
                    }
                });
            }
            else if (methodCode.equals("your turn")) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        your_turn();
                    }
                });
            }
            else if (methodCode.equals("send hand")) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        send_hand(pieces);
                    }
                });
            }
            else if (methodCode.equals("all cards")) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        all_cards(pieces);
                    }
                });
            }
        }
    }
}

