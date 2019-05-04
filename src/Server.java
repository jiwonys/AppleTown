//import java.io.*;
//import java.net.*;
//
//public class Server {
//	static ServerSocket serverSocket;
//	static Socket socket;
//	static DataOutputStream out;
//
//	public void server() throws Exception {
//		System.out.println("Starts AppleTown");
//		serverSocket = new ServerSocket(484848);
//		System.out.println("AppleTown is ONLINE!");
//		
//		socket = serverSocket.accept();
//		System.out.println("Connection from:" + socket.getInetAddress());
//		out = new DataOutputStream(socket.getOutputStream());
//		out.writeUTF("Test");
//		System.out.println("Data has been sent");
//	}
//}
//This class serves the purpose of creating and joining server, but until there is a single player mode finished, this will be set aside.