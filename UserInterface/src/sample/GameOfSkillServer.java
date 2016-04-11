package sample;
//done by Cody
import java.io.*;
import java.net.*;
import java.util.Vector;

public class GameOfSkillServer {
	protected Socket clientSocket           = null;
	protected ServerSocket serverSocket     = null;
	protected ServerThreadHandler[] threads    = null;
	protected int numConnections               = 0;

	public static int SERVER_PORT = 8080;
	public static int MAX_CONNECTIONS = 50;

	public GameOfSkillServer() {
		try {
			serverSocket = new ServerSocket(SERVER_PORT);
			threads = new ServerThreadHandler[MAX_CONNECTIONS];
			while(true) {
				clientSocket = serverSocket.accept();

				threads[numConnections] = new ServerThreadHandler(clientSocket);
				threads[numConnections].start();
				numConnections++;
			}
		} catch (IOException e) {
			System.err.println("IOEXception while creating server connection");
		}
	}

	public static void main(String[] args) {
		GameOfSkillServer app = new GameOfSkillServer();
	}
}
