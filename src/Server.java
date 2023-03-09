import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) throws IOException {
        int port = 1234;
        ExecutorService threadPool = Executors.newWorkStealingPool();

        ServerSocket serverSocket = new ServerSocket(port);

        Socket socket = null;
        while (true) {
            socket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(socket);
            threadPool.submit(clientHandler);
        }
    }
}