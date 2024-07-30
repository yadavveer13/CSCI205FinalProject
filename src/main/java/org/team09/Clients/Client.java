/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2022
 * Instructor: Prof. Brian King
 *
 * Name: Benjamin Bonafede, Veer Yadav, Yacine Bouabida, Alex Coleman
 * Section: CSCI 205 - 02
 * Date: 11/6/22
 * Time: 4:59 PM
 *
 * Project: csci205_final_project
 * Package: org.team09
 * Class: ChatClient

 * Description:
 *
 * ****************************************
 */

package org.team09.Clients;

import javafx.scene.layout.VBox;
import org.team09.ChatController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * This is the chat client program. Each user has their own Client object that connects to the server and the Main
 * Client program
 *
 * <a href="https://www.youtube.com/watch?v=_1nqY-DKP9A"> Inspiration!</a>
 */
public class Client {
    // Username of current user
    private String userName;
    // Socket object to send/receive messages
    private Socket socket;
    // Buffered Reader object to read in messages
    private BufferedReader reader;
    // PrintWriter object sends out messages
    private PrintWriter writer;


    /**
     * Client constructor method, initializes all variables to null and empty values
     */
    public Client() {
        this.userName = "";
        this.socket = null;
        this.writer = null;
        this.reader = null;

    }

    /**
     * Execute method initializes values for Client class, sets socket to an ip value, creates reader and writer objects
     */
    public void execute() {
        try {
            this.socket = new Socket("134.82.186.116", 1500);
            this.writer = new PrintWriter(socket.getOutputStream(), true);
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Takes a string and writes it to the PrintWriter object, thus sending it through the socket
     *
     * @param messageSending - Message to send out
     */
    public void sendMessage(String messageSending) {
        this.writer.println(messageSending);
        this.writer.flush();
    }

    /**
     * Reads in a message from the BufferedReader and adds it to the ChatController's vbox
     *
     * @param vbox - VBox object that displays messages
     */
    public void readMessage(VBox vbox) {
        new Thread(() -> {
            String messageFrom;
            while (true) {
                try {
                    messageFrom = this.reader.readLine();
                    ChatController.addLabel(messageFrom, vbox);
                } catch (IOException e) {
                    System.out.println(e);
                    break;
                }
            }
        }).start();
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Closes socket, writer, and reader when donne
     *
     * @throws IOException if socket is null
     */
    public void closeEverything() throws IOException {
        if (this.socket.isConnected()) {
            this.socket.close();
            System.out.println("closed");
        }
        if (!(this.writer == null)) {
            this.writer.close();
        }
        if (!(this.reader == null)) {
            this.reader.close();
        }

    }

}