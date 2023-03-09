import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private List<ClientHandler> clients = new ArrayList<>();
    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");

                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clients.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
        System.out.println("Client disconnected");
    }

    public static void main(String[] args) {
        int port = 12345;
        Server server = new Server(port);
        server.start();
    }
}

class ClientHandler extends Thread {
    private Socket clientSocket;
    private Server server;
    private PrintWriter out;
    private BufferedReader in;

    public ClientHandler(Socket socket, Server server) {
        this.clientSocket = socket;
        this.server = server;
    }

    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String username = in.readLine();
            System.out.println(username + " connected");

            server.broadcast(username + " joined the chat", this);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.equals("exit")) {
                    break;
                }
                server.broadcast(username + ": " + inputLine, this);
            }

            server.removeClient(this);
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}
