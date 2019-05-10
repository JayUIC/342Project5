package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.Socket;

public class Main extends Application {
    private Client client;
    Label error = new Label("");

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Uno");
        primaryStage.setResizable(false);
        primaryStage.show();

        Pane background = new Pane();
        background.setStyle("-fx-background-color: #532fef;");
        Scene scene = new Scene(background,750,750);
        primaryStage.setScene(scene);

        HBox ip = new HBox(5);
        Label ipLabel = new Label("Enter IP Address:");
        ipLabel.setFont(Font.font("Arial", FontWeight.BOLD,14));
        TextField ipText = new TextField();
        ip.getChildren().addAll(ipLabel,ipText);


        HBox port = new HBox(5);
        Label portLabel = new Label("Enter Port Number");
        portLabel.setFont(Font.font("Arial", FontWeight.BOLD,14));
        TextField portText = new TextField();
        port.getChildren().addAll(portLabel,portText);

        Button go = new Button("GO");
        go.setTextFill(Color.WHITE);
        go.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        go.setOnAction(event ->{
            String address = ipText.getText();
            int portNum = Integer.parseInt(portText.getText());
            getClientSocket(address,portNum,primaryStage);
        });

        error.setTextFill(Color.RED);
        error.setFont(Font.font("Arial",FontWeight.BOLD,12));
        error.relocate(250,400);

        HBox finalBox = new HBox(50);
        finalBox.getChildren().addAll(ip,port,go);
        finalBox.relocate(50,350);

        background.getChildren().addAll(finalBox,error);

    }
    public void getClientSocket(String address, int port, Stage primaryStage){
        try{
            Socket clientSocket = new Socket(address,port);
            error.setText("");
            client = new Client(clientSocket);
            primaryStage.close();
        }
        catch (IllegalArgumentException e){
            error.setText("Server not found. Please try again.");
            System.out.println("Error Found");
        }
        catch (IOException e){
            error.setText("Server not found. Please try again.");
            System.out.println("Error Found");
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
