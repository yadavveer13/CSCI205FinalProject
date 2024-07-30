# CSCI 205 -Software Engineering and Design
Bucknell University  
Lewisburg, PA

### Course Info
Instructor: Brian King 

Semester: Fall 2022

## Team Information
Yacine Bouabida (Scrum Master): Junior Computer Science and Economics Double major. I am a member of the rugby team
here at Bucknell, and I am also a posse scholar.

Veer Yadav (Dev Team): I am a Sophomore Computer Science and Engineering major. Involved all over campus
most notably as philanthropy chair for my fraternity.

Alexander Coleman (Dev Team): Junior Computer Science Major and Economics Double Major.

Ben Bonafede (Product Owner): I am a Sophomore Computer Science Major with an Economics Minor. I also play Varsity lacrosse at Bucknell University.


## Project Information
For our final project we created a secure messaging app hosted on a local network.
We realize that communication is important.

Texting is a discreet and unobtrusive way to communicate. It allows you to send
and receive messages without disturbing others, and it can be done quietly and
privately. This can be useful in a variety of situations, such as when you are in a
meeting, at a conference, or in a public place.

Secure messaging is important for protecting the privacy and security of
communication between individuals or groups. In today's digital world, where
information is often transmitted over the internet, it is essential to ensure
that messages are kept confidential and secure.

A secure messenger app hosted on a network. 
Every client connected to a specific server and is able to chat with anyone else connected to the server.

## How to run it

If a server is already running simply run Mainclient and you should be all set.

Otherwise, run org.team09.Servers.Server first. Make sure the host IP is configured to be the
IP of your personal computer in Client -> execute() method if you wish to host the server
on your personal computer. Then run MainClient. Everyone who wishes to join your server must
also change the IP of their execute method to the IP of your personal computer.

To run this program, you must initialize and run a server. This server will use your own IP to create a server socket. So, in the client class, you 
must make the socket IP to be your own (or whoever is running the server). Finally, after the server is running, one must run MainClient and that will open the chat GUI!

### Package Structure
All of our java code lies under the package main/java/org.team09
Two subpackages exist under org.team09, clients and servers.
The servers package handles the client server network protocol.
The clients package handles how users interact with our messaging app.
Chatcontroller file lies under org.team09 and is the controller for
ChatView.fxml that lies under our /main/resources/ folder.
org.team09 also has a file called encryption that contains our encrypt and decrypt methods.

### Link to our Video Presentation
https://mediaspace.bucknell.edu/media/FinalProjectVideoVersion1/1_hnozleqj

### 3rd Party Libraries

* javafx
* Screenbuilder