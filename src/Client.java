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
            String message = scanner.nextLine();
            out.println(message);
            System.out.println(in.readLine());
        }

    }
}
