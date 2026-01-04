package challenge10.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TcpChatClient {

    public static void main(String... args) {
        String host = "localhost";
        int port = 9090;

        System.out.println("Connecting to server " + host + ":" + port);

        try (
                Socket socket = new Socket(host, port);
                BufferedReader serverIn = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );
                PrintWriter serverOut = new PrintWriter(
                        socket.getOutputStream(), true);
                BufferedReader userIn = new BufferedReader(
                        new InputStreamReader(System.in)
                )
                ) {

            System.out.println("Connected to server");
            System.out.println("Type commands (HELLO, ECHO, TIME, QUIT)");

            String userInput;
            while((userInput = userIn.readLine()) != null) {

                //send user input to server
                serverOut.println(userInput);

                if (serverOut.checkError()) {
                    System.out.println("Failed to send message. Server may be down.");
                    break;
                }

                //read response from server
                String response = serverIn.readLine();

                if (response == null) {
                    System.out.println("Server disconnected. Closing client...");
                    break;
                }

                System.out.println("Server: " + response);

                //Exit if the server says BYE
                if ("Goodbye!".equals(response)) {
                    System.out.println("Disconnected from server.");
                    break;
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
