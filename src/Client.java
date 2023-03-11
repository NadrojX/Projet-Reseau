import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        System.out.print("Enter Username: ");

        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        ClientHandler.setUsername(username);

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        while (scanner.hasNextLine()){
            String message = scanner.nextLine();
            out.println(message);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            System.out.println(in.readLine());
        }

    }
}
