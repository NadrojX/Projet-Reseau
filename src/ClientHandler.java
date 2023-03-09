import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;
    private Server server;
    private PrintWriter out;
    private BufferedReader in;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {

        try {

            out = new PrintWriter(
                    socket.getOutputStream(), true);

            in = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));

            String username = in.readLine();
            System.out.println(username + " is connected");

            String inputLine;
            while ((inputLine   = in.readLine()) != null) {
                if (inputLine.equals("exit")) {
                    break;
                }

                System.out.printf(
                        " PUBLISH author:@%s",
                        username+ "\n" );
                System.out.printf(
                        "----------------------- \n");
                System.out.printf(
                        inputLine + "\n");
                out.println("@" + inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}