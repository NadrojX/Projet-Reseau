import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread{
    private final Socket socket;
    private static String username;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            while (true) {
                String message = in.readLine();
                System.out.println("PUBLISH author:" + username);
                System.out.println("--------------------");
                System.out.println(message);

                out.println("@" + message + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setUsername(String username){
        ClientHandler.username = username;
    }

}
