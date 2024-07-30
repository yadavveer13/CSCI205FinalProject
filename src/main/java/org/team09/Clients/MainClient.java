/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2022
 * Instructor: Prof. Brian King
 *
 * Name: Benjamin Bonafede, Veer Yadav, Yacine Bouabida, Alexander Coleman
 * Section: CSCI 205 - 02
 * Date: 11/6/22
 * Time: 4:59 PM
 *
 * Project: csci205_final_project
 * Package: org.team09
 * Class: MainClient

 * Description: Main Client class that runs the program. Launches username window
 * then opens chat controller
 *
 * ****************************************
 */

package org.team09.Clients;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.team09.ChatController;

import java.io.IOException;

/**
 * Main Client class
 * This is the main client that the user must run after starting a server. It begins by prompting
 * the user to enter a username, then it opens up the chat controller class where it manages the chat
 * application.
 */
public class MainClient extends Application {

    /**
     * Name variable to track current username
     */
    private String name;

    /**
     * Main method for class simply launches run method
     *
     * @param args default
     */
    public static void main(String[] args) {
        launch(args);
    }

    public String getName() {
        return name;
    }

    /**
     * Start method launches Username input screen. Once a valid username is entered,
     * the Chat Controller class is launched and the chat window is presented.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages.
     * @throws IOException if chat controller is null
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        usernameWindow();
        // if name is null (application is exited) we want to not open the Buckchat
        if (this.name == null) {
            System.exit(0);
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ChatView.fxml"));

        loader.setControllerFactory(type -> {
            if (type == ChatController.class) {
                return new ChatController(this.name);
            }
            // default behavior: need this in case there are <fx:include> in the FXML
            try {
                return type.getConstructor().newInstance();
            } catch (Exception exc) {
                // fatal...
                throw new RuntimeException(exc);
            }
        });
        Parent root = loader.load();

        primaryStage.setTitle("BuckChat");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * Username window where the user is prompted to enter their username.
     * Once submit button or enter is pressed, the nameHandler method is called to check for a valid username
     */
    private void usernameWindow() {
        Stage popUpWindow = new Stage();
        popUpWindow.initModality(Modality.APPLICATION_MODAL);
        popUpWindow.setTitle("Username Window");
        Label label1 = new Label("Enter your desired username!");
        TextField textField = new TextField();
        textField.setAlignment(Pos.CENTER);
        textField.setMaxWidth(100);

        Button button1 = new Button("Submit");

        button1.setOnAction(e -> nameHandler(popUpWindow, textField));

        textField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                nameHandler(popUpWindow, textField);
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label1, textField, button1);
        layout.setAlignment(Pos.CENTER);
        Scene scene1 = new Scene(layout, 300, 250);
        popUpWindow.setScene(scene1);
        popUpWindow.showAndWait();
    }

    /**
     * Name Handler method to check for a valid name. If the text field is empty, or the entered name is too long
     * (>20 characters) then an Error pops up and the user is prompted to try again
     *
     * @param popUpWindow - Username window
     * @param textField   where a user can enter the name
     */
    private void nameHandler(Stage popUpWindow, TextField textField) {
        if (textField.getText().isEmpty() || textField.getText().length() > 20) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid Name. Please Try Again!");
            alert.show();
        } else {
            this.name = textField.getText();
            popUpWindow.close();
        }
    }
}
