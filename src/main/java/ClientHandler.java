import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
public class ClientHandler implements Runnable{

    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String message = in.readLine();
            String[] messageLines = message.split("\\\\r\\\\n| ");
            String command = messageLines[0];

            switch (command) {
                case "PUBLISH" -> {
                    String author = messageLines[1].split(":")[1];
                    int contentMessageSize = (message.length() - (messageLines[0].length() + messageLines[1].length() + 9));
                    if (contentMessageSize > 256) {
                        out.println("ERROR\\r\\nMessage too long\\r\\n");
                        return;
                    }
                    StringBuilder completeMessage = new StringBuilder();
                    for (int i = 2; i < messageLines.length; i++) {
                        completeMessage.append(messageLines[i]).append(" ");
                    }
                    System.out.println(author + " " + completeMessage);
                    out.println("OK\\r\\n\\r\\n");
                }
                case "RCV_IDS" -> out.println("OK\\r\\n\\r\\n");
                case "RCV_MSG" -> {
                    int messageId = Integer.parseInt(messageLines[1].split(":")[1]);
                    out.println("Error\\r\\nMessage id not exist\\r\\n");
                }
                default -> out.println("ERROR\\r\\nUnknown command\\r\\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
