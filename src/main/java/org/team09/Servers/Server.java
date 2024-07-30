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
 * Class: Server

 * Description: Server class that must be run before opening main client. Opens a server on port 1500 and
 * sends and receives messages through a socket.
 *
 * ****************************************
 */

package org.team09.Servers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

/**
 * This is the chat server program.
 * Press Ctrl + C to terminate the program.
 *
 * <a href="https://www.codejava.net/java-se/networking/how-to-create-a-chat-console-application-in-java-using-socket"> Inspiration!</a>
 */
public class Server {
    private static int port;
    private final Set<String> userNames = new HashSet<>();
    private final Set<User> userThreads = new HashSet<>();

    public Server() {
        port = 1500;
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.execute();
    }

    /**
     * Executes server class, creates a ServerSocket object with a set port (1500) and waits for other
     * users to connect and accepts them.
     */
    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Chat Server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New user connected");

                User newUser = new User(socket, this);
                newUser.start();
            }

        } catch (IOException ex) {
            System.out.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Delivers a message from one user to others (broadcasting)
     */
    void broadcast(String message, User excludeUser) {
        for (User aUser : this.userThreads) {
            if (aUser != excludeUser) {
                aUser.sendMessage(message);
            }
        }
    }

    /**
     * Broadcasts a message only to the current user (Used to display current active users when you first connect)
     *
     * @param message     - Message to display to current user
     * @param includeUser - Current user object
     */
    void broadcastOnly(String message, User includeUser) {
        for (User aUser : this.userThreads) {
            if (aUser == includeUser) {
                aUser.sendMessage(message);
            }
        }
    }

    /**
     * Stores username of the newly connected client.
     */
    void addUserName(String userName) {
        this.userNames.add(userName);
    }

    /**
     * When a client is disconnected, removes the associated username and UserThread
     */
    void removeUser(String userName, User aUser) {
        boolean removed = this.userNames.remove(userName);
        if (removed) {
            this.userThreads.remove(aUser);
            System.out.println("The user " + userName + " left!");
        }
    }

    Set<String> getUserNames() {
        return this.userNames;
    }

    /**
     * Returns true if there are other users connected (not count the currently connected user)
     */
    boolean hasUsers() {
        return !this.userNames.isEmpty();
    }

    public void addUserThread(User user) {
        this.userThreads.add(user);
    }
}