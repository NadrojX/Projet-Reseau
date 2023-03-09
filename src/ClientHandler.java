import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        PrintWriter out = null;
        BufferedReader in = null;
        try {

            out = new PrintWriter(
                    socket.getOutputStream(), true);

            in = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {

                System.out.printf(
                        " Sent from the client: %s\n",
                        line);
                String username = line;

                out.println("@" + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}