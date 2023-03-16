import java.io.*;
import java.net.Socket;
import java.util.Objects;
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
            String[] responseLines = response.split("\\\\r\\\\n");
            String header = responseLines[0];
            if(responseLines.length >= 2){
                String corps = responseLines[1];
                if(header.equals("ERROR")){
                    System.out.println("ERROR");
                    System.out.println(corps);
                    socket.close();
                }
            } else {
                if(header.equals("OK")){
                    System.out.println("OK");
                    socket.close();
                }
            }
        }

    }
}
