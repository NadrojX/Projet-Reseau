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
                String[] requestLines = request.split("\\\\r\\\\n");
                String header = requestLines[0];
                if (header.startsWith("PUBLISH")) {
                    System.out.println(header);
                    if(requestLines.length >= 2) {
                        String corps = requestLines[1];
                        System.out.println(corps);
                        out.println("OK\\\\r\\\\n\\\\r\\\\n");
                    } else {
                        out.println("ERROR\\\\r\\\\nThere is an error\\\\r\\\\n");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
