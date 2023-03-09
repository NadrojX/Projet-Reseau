import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        if(args.length < 2){
            System.out.println("Enter the server and the port!");
            return;
        }

        String server = args[0];
        int port = Integer.parseInt(args[1]);

        Socket socket = new Socket(server, port);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose a username > ");


        while (scanner.hasNextLine()) {
            String input = scanner.nextLine() + " ";
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            output.writeUTF(input);
        }
    }
}