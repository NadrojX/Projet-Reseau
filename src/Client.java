import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        Scanner scanner = new Scanner(System.in);

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        while (true){
            String request = scanner.nextLine();
            out.println(request);

            String response = in.readLine();
            String[] responseLines = response.split("\\\\r\\\\n| ");
            String header = responseLines[0];

            if (header.startsWith("OK")) {
                System.out.println("OK");
                socket.close();
            } else if (header.startsWith("ERROR")) {
                System.out.println("ERROR");
                socket.close();
            }
        }

    }
}
