/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2022
 * Instructor: Prof. Brian King
 *
 * Name: Benjamin Bonafede, Veer Yadav, Yacine Bouabida, Alex Coleman
 * Section: CSCI 205 - 02
 * Date: 11/13/22
 * Time: 4:41 PM
 *
 * Project: csci205_final_project
 * Package: PACKAGE_NAME
 * Class: org.team09.ChatController

 * Description: Chat Client Controller
 *
 * ****************************************
 */
package org.team09;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.team09.Clients.Client;

import java.io.IOException;

/**
 * Chat Client Controller class, handles all actions performed on the main chat scene.
 * Adds the messages received to text stream and captures text from text field and broadcasts messages
 *
 * <a href="https://www.youtube.com/watch?v=_1nqY-DKP9A"> Inspiration!</a>
 */
public class ChatController {

    private final String userName;
    /**
     * Private instances of different javaFX objects including Button, AnchorPanne, etc.
     */
    @FXML
    private Button quitButton;
    @FXML
    private ScrollPane mainSP;
    @FXML
    private TextField messageField;
    @FXML
    private VBox messageVBox;
    @FXML
    private Button sendButton;


    /**
     * Constructor initializes ChatController with the current user's username
     *
     * @param name - Current user's username
     */
    public ChatController(String name) {
        this.userName = name;
    }

    /**
     * Receives message and displays it on the user's screen
     *
     * @param messageFrom - message receievd from other clients through server
     * @param vbox        - VBox object used in chat scene
     */
    public static void addLabel(String messageFrom, VBox vbox) {
        if (!messageFrom.contains("USERQUIT2022")) { //make sure we don't send message when user quits
            HBox hbox = new HBox();
            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.setPadding(new Insets(5, 5, 5, 10));


            Color color = Color.ORANGE.darker();
            Text text = new Text(messageFrom);
            text.setFill(color);
            TextFlow textFlow = new TextFlow(text);
            textFlow.setPadding(new Insets(5, 10, 5, 10));

            hbox.getChildren().add(textFlow);

            Platform.runLater(() -> vbox.getChildren().add(hbox));
        }
    }

    /**
     * Initializes the new client object with the entered username
     */
    @FXML
    void initialize() {
        Client client = new Client();
        client.execute();
        client.readMessage(this.messageVBox);

        client.setUserName(this.userName);
        client.sendMessage(client.getUserName());

        // automatically makes scroll pane go to most recent text
        this.messageVBox.heightProperty().addListener((observable, oldValue, newValue) -> mainSP.setVvalue((Double) newValue));

        client.readMessage(this.messageVBox);

        initHandlers(client);
    }

    /**
     * Handler method for sending messages and closing application when quit button is pressed
     *
     * @param client - current user client
     */
    private void initHandlers(Client client) {

        this.sendButton.setOnAction(event -> sendMessageHelper(client));

        this.messageField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                sendMessageHelper(client);
            }
        });

        this.quitButton.setOnAction(event -> {
            try {
                client.sendMessage("USERQUIT2022");
                client.closeEverything();
                Platform.exit();
            } catch (IOException e) {
                System.out.println(e);
            }
        });
    }

    /**
     * Helper method for sending messages. Takes characters from text field and displays it on user screen
     * while broadcasting message to the server. Clears text field after sent.
     *
     * @param client - current user client
     */
    private void sendMessageHelper(Client client) {
        String messageSend = this.messageField.getText();
        if (!messageSend.isEmpty()) {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5, 5, 5, 10));

            Color myColor = Color.DARKBLUE;
            Text text = new Text(messageSend);
            text.setFill(myColor);
            TextFlow textFlow = new TextFlow(text);
            textFlow.setPadding(new Insets(5, 10, 5, 10));

            hBox.getChildren().add(textFlow);
            this.messageVBox.getChildren().add(hBox);


            client.sendMessage(messageSend);
            this.messageField.clear();
        }
    }


}

