import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        ServerSocket serverSocket = new ServerSocket(12345);

        while (true){
            Socket socket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(socket);
            executorService.submit(clientHandler);
        }
    }

}
