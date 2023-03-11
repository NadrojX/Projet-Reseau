import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread{
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            while (true) {
                String request = in.readLine();
                String[] requestParts = request.split(" ");

                switch (requestParts[0]) {
                    case "PUBLISH":
                        if(requestParts.length < 3) {
                            out.println("ERROR");
                        } else {
                            out.println("OK");
                        }
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
