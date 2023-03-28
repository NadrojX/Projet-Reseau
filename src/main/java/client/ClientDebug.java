package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientDebug {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        Scanner scanner = new Scanner(System.in);

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        while (true) {
            String message = scanner.nextLine();
            out.println(message);

            String response = in.readLine();
            System.out.println(response);
        }
    }
}
