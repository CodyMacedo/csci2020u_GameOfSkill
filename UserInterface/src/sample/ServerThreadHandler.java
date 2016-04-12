package sample;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServerThreadHandler extends Thread {
	protected Socket socket       = null;
	protected PrintWriter out     = null;
	protected BufferedReader in   = null;


	public ServerThreadHandler(Socket socket) {
		super();
		this.socket = socket;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			System.err.println("IOEXception while opening a read/write connection");
		}
	}

	public void run() {
		boolean endOfSession = false;
		while(!endOfSession) {
			endOfSession = processCommand();
		}
		try {
			socket.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	protected boolean processCommand() {
		String message = null;
		try {
			message = in.readLine();
		} catch (IOException e) {
			System.err.println("Error reading command from socket.");
			return true;
		}
		if (message == null) {
			return true;
		}
		StringTokenizer st = new StringTokenizer(message);
		String command = st.nextToken();
		String args = null;
		if (st.hasMoreTokens()) {
			args = message.substring(command.length()+1, message.length());
		}
		return processCommand(command, args);
	}
	
	protected boolean processCommand(String command, String args) {
		if (command.equalsIgnoreCase("LEADERBOARD")) {
			File file= null;
				if (args.equals("1")) {
					file = new File("./ServerFiles/leaderEasy.txt");
			} else if (args.equals("2")) {
				file = new File("./ServerFiles/leaderMedium.txt");
			} else {
				file = new File("./ServerFiles/leaderHard.txt");
			}
			try{
				BufferedReader br = new BufferedReader(new FileReader(file));
				String score;
				while ((score = br.readLine()) != null) {
					out.println(score.substring(score.indexOf(" ") + 1) + "   " + score.substring(0, score.indexOf(" ")));
				}
				out.println("[END]");
				br.close();

			}catch (Exception e){
				e.printStackTrace();
			}
			
			return false;
		} else if (command.equalsIgnoreCase("NEWSCORE")) {
			File file= null;
			if (args.substring(0,1).equals("1")) {	
				file = new File("./ServerFiles/leaderEasy.txt");
			} else if (args.substring(0,1).equals("2")) {
				file = new File("./ServerFiles/leaderMedium.txt");
			} else {
				file = new File("./ServerFiles/leaderHard.txt");
			}
			
			ArrayList<String> items = new ArrayList<String>();

			try{
				BufferedReader br = new BufferedReader(new FileReader(file));
				String score;
				while ((score = br.readLine()) != null) {
					items.add(score);
				}
				items.add(args.substring(1));

				Collections.sort(items, new Comparator<String>() {
					public int compare(String e1, String e2) {
						Integer i1 = Integer.parseInt(e1.substring(0, e1.indexOf(" ")));
						Integer i2 = Integer.parseInt(e2.substring(0, e2.indexOf(" ")));
						return i1.compareTo(i2);
					}
				});

				FileWriter writer = new FileWriter(file,false);
				for (int i = 0; i < items.size(); i++) {
					writer.write(items.get(i) + "\n");
				}
				writer.close();

				br.close();

			}catch(Exception e){
				e.printStackTrace();
			}
			
			return false;
		} else if (command.equalsIgnoreCase("QUIT")){
			return true;
		} else {
			out.println("400 Unrecognized Command: "+command);
			return false;
		}
	}
}
