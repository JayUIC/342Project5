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
    HBox cardTable = new HBox(10);
    VBox centerPile = new VBox();

    Label p1 = new Label("");
    Label p2 = new Label("");
    Label p3 = new Label("");
    Label p4 = new Label("");

    VBox playerCardCount = new VBox(5);
    ScrollPane gameHand = new ScrollPane();


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
        listener.start();
        Pane lobbyPane = new Pane();
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

    }

    public void all_cards(int playerNumber, int numCards) {
        if (playerNumber == 1){
            p1.setText("Number of Cards Player 1 has: " + numCards);
        }
        else if (playerNumber == 2){
            p2.setText("Number of Cards Player 2 has: " + numCards);
        }
        else if (playerNumber == 3){
            p3.setText("Number of Cards Player 3 has: " + numCards);
        }
        else{
            p4.setText("Number of Cards Player 4 has: " + numCards);
        }
    }

    public void message(String message) {
        consoleMessage.setText(message);
    }

    private void makeHandSelectable(){
        for(Card c : hand){
            c.cardImage.setOnMouseClicked(event -> {
                centerPile.getChildren().add(c.cardImage);
                cardTable.getChildren().remove(c.cardImage);
                makeHandUnSelectable();
                out.println("Turn;play;" + c.color + ";" + c.name);
            });
        }
    }
    private void makeHandUnSelectable(){
        for(Card c : hand){
            c.cardImage.setOnMouseClicked(event -> {

            });
        }
    }

    public void your_turn() {
        makeHandSelectable();
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
            cardTable.getChildren().add(newCard.cardImage);
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

        playerCardCount.getChildren().addAll(p1,p2,p3,p4);
        playerCardCount.relocate(265,85);
        Background.getChildren().add(playerCardCount);

        /*String[] nums = {"0","0","0","0","0"};
        all_cards(nums);*/

    }
    public void createGameConsoleGUI(){
        consoleMessage.setFont(Font.font("Arial", FontWeight.BOLD,18));
        consoleMessage.setTextFill(Color.LIGHTGREEN);
        consoleMessage.setText("Welcome to UNO");

        gameConsole.relocate(175,0);
        gameConsole.setMinHeight(75);
        gameConsole.setMinWidth(375);
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

        Button draw = new Button("Draw");
        draw.setTextFill(Color.WHITE);
        draw.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        draw.setOnAction(event -> {
            out.println("Turn;draw");
        });
        draw.relocate(600, 25);

        gameHand.setStyle("-fx-background-color: transparent");
        cardTable.setStyle("-fx-background-color: transparent;");
        cardTable.setMinHeight(120);

        gameHand.setContent(cardTable);
        gameHand.setFitToHeight(true);
        gameHand.setPrefSize(500, 150);
        gameHand.relocate(100,575);
        gameHand.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        gameHand.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        centerPile.relocate(300,300);

        Background.getChildren().addAll(draw,gameHand,centerPile);

    }

    public ImageView getCardImage(String name, String color) {
        Image card = new Image("/sample/assets/"+color+"_"+name+".png");
        ImageView IVcard = new ImageView(card);
        return IVcard;
    }

    class Card {
        private String name;
        private String color;
        private ImageView cardImage;

        Card(String name, String color) {
            this.name = name;
            this.color = color;
            this.cardImage = getCardImage(name, color);
            cardImage.setFitWidth(80);
            cardImage.setFitHeight(120);
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
                        all_cards(Integer.parseInt(pieces[1]), Integer.parseInt(pieces[2]));
                    }
                });
            }
        }
    }
}

