import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientHandler extends Thread{
    private final Socket socket;
    private final static HashMap<Long, String> messagesID = new HashMap<>();
    private final static HashMap<String, String> messagesAuthor = new HashMap<>();
    private final static HashMap<Long, String> messagesKeyWorld = new HashMap<>();
    private final static ArrayList<String> keyWorld = new ArrayList<>();

    private static long id = 1;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            while (true) {
                String request = in.readLine();
                String[] requestLines = request.split("\\\\r\\\\n| ");

                String header = requestLines[0];

                if (header.equals("PUBLISH")) {
                    String author = requestLines[1];
                    StringBuilder message = new StringBuilder();
                    if(requestLines.length >= 3 && requestLines.length <= 259) {
                        for (int i = 2; i < requestLines.length; i++) {
                            if (requestLines[i].startsWith("#")) {
                                keyWorld.add(requestLines[i]);
                            }
                            message.append(requestLines[i]).append(" ");
                        }
                        messagesID.put(getId(), message.toString());
                        messagesAuthor.put(message.toString(), author);
                        for (String s : keyWorld) {
                            messagesKeyWorld.put(getId(), s);
                        }
                        updateId();
                        out.println("OK\r\n\r\n");
                    } else {
                        out.println("ERROR\\r\\nThere is an error your message need to make 256 characters maximum.\\r\\n");
                    }
                }

                if(header.equals("RCV_IDS")){

                }

                if(header.equals("RCV_MSG")){
                    requestLines = request.split("\\\\r\\\\n| |:");
                    long id = Long.parseLong(requestLines[2]);
                    if(messagesID.containsKey(id)) {
                       String message = messagesID.get(id);
                       out.println("MSG\\r\\n" + message);
                    } else {
                        out.println("ERROR\\r\\nThere is no message with this id.\\r\\n");
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized void updateId() {
        id++;
    }

    public synchronized long getId() {
        return id;
    }

}
