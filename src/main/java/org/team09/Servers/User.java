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
 * Class: User

 * Description: User class that holds username and contains the send and receive methods
 *
 * ****************************************
 */

package org.team09.Servers;


import java.io.*;
import java.net.Socket;


/**
 * This thread handles connection for each connected client, so the server
 * can handle multiple clients at the same time.
 *
 * <a href="https://www.codejava.net/java-se/networking/how-to-create-a-chat-console-application-in-java-using-socket"> Inspiration!</a>
 */
public class User extends Thread {

    private final Socket socket;
    private final Server server;
    /**
     * Private variables for User class
     */
    private String userName;
    private PrintWriter writer;


    /**
     * User class constructor, Takes in server and socket objects and sets current username to blank
     *
     * @param socket used to make connection
     * @param server instance that will send a user's message
     */
    public User(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        this.userName = "";
    }

    /**
     * Sets up innput and output streams for socket object to send and receive messages, displays current connected
     * users. While the program is still running, sends and receives messages through socket..
     */
    public void run() {
        try {
            InputStream input = this.socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = this.socket.getOutputStream();
            this.writer = new PrintWriter(output, true);

            printUsers();

            this.userName = reader.readLine();
            this.server.addUserName(this.userName);
            this.server.addUserThread(this);

            String serverMessage = "New user connected: " + this.userName;
            this.server.broadcast(serverMessage, this);

            String clientMessage;
            do {
                clientMessage = reader.readLine();
                if (clientMessage.contains("USERS")) { // prints active users to user who asked
                    serverMessage = String.format("Active users are: %s", this.server.getUserNames().toString());
                    this.server.broadcastOnly(serverMessage, this);
                } else {
                    serverMessage = "[" + this.userName + "]: " + clientMessage;
                    this.server.broadcast(serverMessage, this);
                }

            } while (!clientMessage.contains("USERQUIT2022"));

            this.server.removeUser(this.userName, this);
            this.socket.close();

            serverMessage = this.userName + " has left.";
            this.server.broadcast(serverMessage, this);

        } catch (IOException ex) {
            System.out.println("Error in UserThread: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Sends a list of online users to the newly connected user.
     */
    void printUsers() {
        String writerUsers;
        if (this.server.hasUsers()) {
            writerUsers = String.format("Connected users: " + this.server.getUserNames());
            this.writer.println(writerUsers);
        } else {
            writerUsers = "No other users connected";
            this.writer.println(writerUsers);
        }
    }

    /**
     * Sends a message to the client.
     */
    void sendMessage(String message) {
        this.writer.println(message);
        this.writer.flush();
    }


}